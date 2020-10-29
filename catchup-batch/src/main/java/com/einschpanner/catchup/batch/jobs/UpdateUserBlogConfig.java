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
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
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
public class UpdateUserBlogConfig {
    private final UserQueryRepository userQueryRepository;
    private final BlogRepository blogRepository;

    @Bean
    public Job updateUserBlogJob(
            JobBuilderFactory jobBuilderFactory,
            Step updateUserBlogJobStep
    ) {
        log.info("********** This is updateUserBlogJob");
        return jobBuilderFactory.get("updateUserBlogJob")
//                .preventRestart()
                .start(updateUserBlogJobStep)
                .build();
    }

    @Bean
    @JobScope
    public Step updateUserBlogJobStep(
            StepBuilderFactory stepBuilderFactory
    ) {
        log.info("********** This is updateUserBlogJobStep");
        return stepBuilderFactory.get("updateUserBlogJobStep")
                .<User, List<Blog>>chunk(10)
                .reader(this.updateUserBlogReader())
                .processor(this.updateUserBlogProcessor())
                .writer(this.updateUserBlogWriter())
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<User> updateUserBlogReader() {
        log.info("********** This is updateUserBlogReader");
        List<User> users = userQueryRepository.findAllByExistsBlogRss();
        log.info("          - activeMember SIZE : " + users.size());
        return new ListItemReader<>(users);
    }

    // 비즈니스 로직
    public ItemProcessor<User, List<Blog>> updateUserBlogProcessor() {
        return user -> {
            log.info("********** This is updateUserBlogProcessor");

            List<Blog> blogs = new ArrayList<>();

            try {
                URL feedSource = new URL(user.getAddrRss());
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedSource));
                for (int i = 0; i < feed.getEntries().size(); i++) {
                    SyndEntry entry = (SyndEntry) feed.getEntries().get(i);

                    String title = entry.getTitle();
                    String link = entry.getLink();
                    String description = getDescription(entry.getDescription().getValue());
                    LocalDateTime publishedDate = new java.sql.Timestamp(
                            entry.getPublishedDate().getTime()).toLocalDateTime();

                    Blog blog = Blog.builder()
                            .title(title)
                            .link(link)
                            .description(description)
                            .publishedDate(publishedDate)
                            .user(user)
                            .build();
                    blogs.add(blog);
                }
            } catch (FeedException | IOException e) {
                e.printStackTrace();
            }

            return blogs;
        };
    }

    public ItemWriter<List<Blog>> updateUserBlogWriter() {
        log.info("********** This is updateUserBlogWriter");
        return (
                (List<? extends List<Blog>> allBlogs) -> {
                    for (List<Blog> blogs : allBlogs) {
                        saveOrUpdate(blogs);
                    }
                }
        );
    }

    private String getDescription(String parsing) {
        Document doc = Jsoup.parse(parsing);
        String description = doc.text();

        if (description.length() > 150) {
            description = description.substring(0, 150).concat("…");
        }
        return description;
    }

    @Transactional
    void saveOrUpdate(List<Blog> blogs){
        for (Blog blog : blogs) {
            Optional<Blog> optional = blogRepository.findByLink(blog.getLink());
            if (optional.isPresent()) {
                Blog present = optional.get();
                blogRepository.save(present.updateBlog(blog));
            } else {
                blog.initCntLike();
                blogRepository.save(blog);
            }
        }
    }
}
