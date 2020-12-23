package com.einschpanner.catchup.batch.jobs;

import com.einschpanner.catchup.TestBatchConfig;
import com.einschpanner.catchup.domain.blog.dao.BlogRepository;
import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.Role;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 스프링 배치 4.1에서 새로운 어노테이션이 추가되었습니다.
 * 바로 @SpringBatchTest입니다.
 * <p>
 * 자동으로 등록되는 빈은 총 4개입니다.
 * <p>
 * - JobLauncherTestUtils
 * 스프링 배치 테스트에 필요한 전반적인 유틸 기능들을 지원
 * <p>
 * - JobRepositoryTestUtils
 * DB에 생성된 JobExecution을 쉽게 생성/삭제 가능하게 지원
 * <p>
 * - StepScopeTestExecutionListener
 * 배치 단위 테스트시 StepScope 컨텍스트를 생성
 * 해당 컨텍스트를 통해 JobParameter등을 단위 테스트에서 DI 받을 수 있음
 * <p>
 * - JobScopeTestExecutionListener
 * 배치 단위 테스트시 JobScope 컨텍스트를 생성
 * 해당 컨텍스트를 통해 JobParameter등을 단위 테스트에서 DI 받을 수 있음
 */


/**
 * spring batch 테스트 이후 lazy loading 이슈 참고
 * batch에선 @Transactional을 사용할 수 없어 lazy loading 이슈 발생
 * https://stackoverflow.com/questions/60610021/how-to-test-spring-batch-job-within-transactional-springboottest-test-case
 */

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest(classes = {BlogRssParserConfig.class, TestBatchConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class BlogRssParserConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    protected PlatformTransactionManager transactionManager;

    private TransactionTemplate transactionTemplate;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        blogRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

        user1 = User.builder()
                .userId(1L)
                .nickname("Ryanghwa")
                .email("Ryanghwa@naver.com")
                .addrRss("https://rss.blog.naver.com/dlfidghk.xml")
                .role(Role.USER)
                .build();
        user2 = User.builder()
                .userId(2L)
                .nickname("Jinyoung")
                .email("Jinyoung@naver.com")
                .addrRss("https://dbbymoon.tistory.com/rss")
                .role(Role.USER)
                .build();

        List<User> users = new ArrayList<>(
                Arrays.asList(user1, user2)
        );
        userRepository.saveAll(users);
    }

    @BeforeEach
    public void setUpTransactionTemplate() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    public void 배치_동작_테스트() throws Exception {

        // When
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // Then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        // user의 rss 주소가 유효하다고 가정
        transactionTemplate.execute(status -> {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                assertThat(user.getBlogs().size()).isGreaterThan(1);

                for (Blog blog : user.getBlogs()) {
                    log.info(blog.getTitle());
                }
            }
            return "OK";
        });
    }
}