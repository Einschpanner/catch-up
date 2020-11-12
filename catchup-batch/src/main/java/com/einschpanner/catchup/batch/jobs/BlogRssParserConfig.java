package com.einschpanner.catchup.batch.jobs;

import com.einschpanner.catchup.batch.common.reader.QuerydslPagingItemReader;
import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.blog.domain.QBlog;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.einschpanner.catchup.domain.user.domain.QUser.user;
import static com.einschpanner.catchup.domain.blog.domain.QBlog.blog;

/**
 * Ref : https://ahndy84.tistory.com/19?category=339592
 * Reader와 Processor에서는 1건씩 다뤄지고, Writer에선 Chunk 단위로 처리된다는 것만 기억
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BlogRssParserConfig {
    public static final String JOB_NAME = "blogRssParserJob";
    public static final String STEP_NAME = JOB_NAME + "_step";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final BlogRssParserJobParameter jobParameter; // 생성한 빈을 바로 DI 받는다.

    private final int chunkSize = 5;

    @Bean
    @JobScope
    public BlogRssParserJobParameter jobParameter() {
        return new BlogRssParserJobParameter();
    }

    @Bean(name = JOB_NAME)
    public Job job() {
        log.info("********** This is " + JOB_NAME);
        return jobBuilderFactory.get(JOB_NAME)
//                .preventRestart()
                .start(step())
                .build();
    }

    @Bean(name = STEP_NAME)
    @JobScope
    @Transactional
    public Step step() {
        log.info("********** This is " + STEP_NAME);

        return stepBuilderFactory.get(STEP_NAME)
                .<User, User>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writerList())
                .build();
    }

    @Bean
    @StepScope
    @Transactional
    public QuerydslPagingItemReader<User> reader() {
        log.info("********** This is " + JOB_NAME + "_reader");

        return new QuerydslPagingItemReader<>(entityManagerFactory, chunkSize, queryFactory ->
                queryFactory.selectFrom(user)
                        .leftJoin(user.blogs, blog)
                        .fetchJoin()
                        .where(user.addrRss.isNotNull())
                        .orderBy(user.userId.asc())
        );
    }

    // 비즈니스 로직
    @Transactional
    public ItemProcessor<User, User> processor() {
        return user -> {
            log.info("********** This is " + JOB_NAME + "_processor");
            log.info("userId : {}, name : {}", user.getUserId(), user.getNickname());

            Map<String, Blog> map = new HashMap<>();

            for (Blog b : user.getBlogs()) {
                map.put(b.getLink(), b);
                System.out.println(b.getTitle());
            }

            List<Blog> newBlogs = buildBlogList(user);
            for (Blog newBlog : newBlogs) {
                String link = newBlog.getLink();
                Blog exists = map.get(link);
                if (exists == null) user.addBlog(newBlog);
                else exists.update(newBlog); // 기존에 있는건 업데이트 하도록
            }

            log.info("{}", user.getBlogs().size());
            return user;
        };
    }

    @Transactional
    public JpaItemWriter<User> writerList() {
        log.info("********** This is " + JOB_NAME + "_writer");

        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    private List<Blog> buildBlogList(User user){
        List<Blog> newBlogs = new ArrayList<>();

        try {
            URL feedSource = new URL(user.getAddrRss());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));

            for (Object object : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) object;

                Blog newBlog = buildBlog(user, entry);
                newBlogs.add(newBlog);
            }
        } catch (FeedException | IOException e) {
            e.printStackTrace();
        }

        return newBlogs;
    }

    /**
     * User, SyndEntry로 새로운 Blog 인스턴스 생성
     */
    private Blog buildBlog(User user, SyndEntry entry) throws IOException {

        String title = entry.getTitle();
        String link = entry.getLink();
        String description = getDescription(entry.getDescription().getValue());
        String urlThumbnail = getUrlThumbnail(link);
        LocalDateTime publishedDate = getLocalDateTime(entry.getPublishedDate().getTime());

        return Blog.builder()
                .title(title)
                .link(link)
                .description(description)
                .urlThumbnail(urlThumbnail)
                .publishedDate(publishedDate)
                .user(user)
                .cntLike(0)
                .build();
    }

    /**
     * 페이지 Description 가져오기
     */
    private String getDescription(String parsing) {
        Document doc = Jsoup.parse(parsing);
        String description = doc.text();

        if (description.length() > 150) {
            description = description.substring(0, 150).concat("…");
        }
        return description;
    }

    /**
     * URL 썸네일 가져오기
     * + Naver Blog 같은 경우 iframe에 감춰져 있어 한 번 더 검사함
     * + 그래도 없으면 img 태그 서치
     */
    private String getUrlThumbnail(String url) throws IOException {
        String urlThumbnail = null;

        // open graph 태그 검사
        Document doc = Jsoup.connect(url).timeout(5000).get();
        urlThumbnail = doc.select("meta[property=og:image]").attr("content");
        if (!urlThumbnail.isEmpty()) return urlThumbnail;

        // iframe 검사
        Elements elements = doc.select("iframe[src]");
        if (!elements.isEmpty()) {
            String iframeUrl = elements.first().attr("abs:src");
            Document iframeDoc = Jsoup.connect(iframeUrl).timeout(3000).get();
            urlThumbnail = iframeDoc.select("meta[property=og:image]").attr("content");
            if (!urlThumbnail.isEmpty()) return urlThumbnail;
        }

        // img 태그 검사
        urlThumbnail = doc.getElementsByTag("img").first().absUrl("src");
        return urlThumbnail;
    }

    /**
     * Date to LocalDateTime 메서드
     */
    private LocalDateTime getLocalDateTime(long time) {
        return new java.sql.Timestamp(time).toLocalDateTime();
    }
}