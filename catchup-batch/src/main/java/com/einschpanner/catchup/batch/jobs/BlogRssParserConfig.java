package com.einschpanner.catchup.batch.jobs;

import com.einschpanner.catchup.domain.blog.dao.BlogRepository;
import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.user.dao.UserQueryRepository;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Ref : https://ahndy84.tistory.com/19?category=339592
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

    private final int chunkSize = 10;

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
    public Step step() {
        log.info("********** This is " + STEP_NAME);
        return stepBuilderFactory.get(STEP_NAME)
                .<User, List<Blog>>chunk(chunkSize)
                .reader(this.reader())
                .processor(this.processor())
                .writer(this.writer())
                .build();
    }

    @Bean
    public JpaPagingItemReader<User> reader() {
        final String query =
                "SELECT u " +
                "FROM User u " +
                "JOIN FETCH u.blogs ub " +
                "WHERE u.addrRss IS NOT NULL " +
                "ORDER BY u.userId";

        log.info("********** This is " + JOB_NAME + "_reader");
//        List<User> users = userQueryRepository.findAllByExistsBlogRss();
//        log.info("          - activeMember SIZE : " + users.size());
//        return new ListItemReader<>(users);
        return new JpaPagingItemReaderBuilder<User>()
                .name("reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString(query)
                .build();
    }

    // 비즈니스 로직
    @Bean
    public ItemProcessor<User, List<Blog>> processor() {
        return user -> {
            log.info("********** This is " + JOB_NAME + "_processor");

            List<Blog> blogs = new ArrayList<>();

            try {
                URL feedSource = new URL(user.getAddrRss());
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedSource));

                for (Object object : feed.getEntries()) {
                    SyndEntry entry = (SyndEntry) object;

                    Blog blog = buildBlog(user, entry);
                    blogs.add(blog);
                }
            } catch (FeedException | IOException e) {
                e.printStackTrace();
            }

            return blogs;
        };
    }

    @Bean
    public ItemWriter<List<Blog>> writer() {
        log.info("********** This is " + JOB_NAME + "_writer");
        return items -> {
            for (List<Blog> list : items) {
                for (Blog blog : list) {
                    log.info(blog.toString());
                }
            }
        };
    }

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
                .build();
    }

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
     * + Naver Blog 같은 경우 iframe에 감춰져 있었음
     */
    private String getUrlThumbnail(String url) throws IOException {
        String urlThumbnail = null;

        Document doc = Jsoup.connect(url).timeout(4000).get();
        urlThumbnail = doc.select("meta[property=og:image]").attr("content");
        if (!urlThumbnail.isEmpty()) return urlThumbnail;

        Elements elements = doc.select("iframe[src]");
        if (elements.isEmpty()) return null;

        String iframeUrl = elements.get(0).attr("abs:src");
        doc = Jsoup.connect(iframeUrl).timeout(4000).get();
        urlThumbnail = doc.select("meta[property=og:image]").attr("content");
        return urlThumbnail;
    }

    /**
     * Date to LocalDateTime 메서드
     */
    private LocalDateTime getLocalDateTime(long time) {
        return new java.sql.Timestamp(time).toLocalDateTime();
    }

//    @Transactional
//    void saveOrUpdate(List<Blog> blogs) {
//        for (Blog blog : blogs) {
//            Optional<Blog> optional = blogRepository.findByLink(blog.getLink());
//            if (optional.isPresent()) {
//                Blog present = optional.get();
//                blogRepository.save(present.updateBlog(blog));
//            } else {
//                blog.initCntLike();
//                blogRepository.save(blog);
//            }
//        }
//    }
}
