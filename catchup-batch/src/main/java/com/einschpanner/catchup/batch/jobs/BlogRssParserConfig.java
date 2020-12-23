package com.einschpanner.catchup.batch.jobs;

import com.einschpanner.catchup.batch.common.patitioner.RangePartitioner;
import com.einschpanner.catchup.batch.common.processor.SaveOrUpdateBlogItemProcessor;
import com.einschpanner.catchup.batch.common.reader.SingleTxQuerydslPagingItemReader;
import com.einschpanner.catchup.batch.common.writer.SingleTxJpaItemWriter;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.einschpanner.catchup.domain.user.domain.QUser.user;

/**
 * Ref : https://ahndy84.tistory.com/19?category=339592
 * Reader와 Processor에서는 1건씩 다뤄지고, Writer에선 Chunk 단위로 처리된다는 것만 기억
 */

/**
 * Fetch Join 된 결과는 limit이 안걸리기 때문에 reader에서 Fetch join 제거
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BlogRssParserConfig {
    public static final String JOB_NAME = "blogRssParserJob";
    public static final String STEP_NAME = JOB_NAME + "_partitioned_step";
    public static final String MASTER_NAME = JOB_NAME + "_master_step";

    @PersistenceContext
    private final EntityManager entityManager;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RangePartitioner partitioner;
    private final BlogRssParserJobParameter jobParameter; // 생성한 빈을 바로 DI 받는다.

    private final int chunkSize = 5;
    private final int gridSize = 4;
    private final int threads = 4;

    @Bean
    @JobScope
    public BlogRssParserJobParameter jobParameter() {
        return new BlogRssParserJobParameter();
    }

    @Bean
    public Job job() {
        log.info("********** This is " + JOB_NAME);
        return jobBuilderFactory.get(JOB_NAME)
                .flow(masterStep())
                .end()
                .build();
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory.get(MASTER_NAME)
                .<User, User>partitioner(STEP_NAME, partitioner)
                .step(step())
                .gridSize(gridSize)
                .taskExecutor(taskExecutor())
                .build();
    }

    protected ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("task-thread-");
        executor.setCorePoolSize(threads);
        executor.initialize();
        return executor;
    }

    @Bean
    public Step step() {
        log.info("********** This is " + STEP_NAME);
        return stepBuilderFactory.get(STEP_NAME)
                .<User, User>chunk(chunkSize)
                .reader(reader(null, null))
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public SingleTxQuerydslPagingItemReader<User> reader(
            @Value("#{stepExecutionContext[min]}") Integer min,
            @Value("#{stepExecutionContext[max]}") Integer max
    ) {
        log.info("********** This is " + JOB_NAME + "_reader");
        SingleTxQuerydslPagingItemReader<User> reader = new SingleTxQuerydslPagingItemReader<>(entityManager, chunkSize, queryFactory ->
                queryFactory.selectFrom(user)
//                        .leftJoin(user.blogs, blog)
//                        .fetchJoin()
                        .where(user.addrRss.isNotNull(), user.userId.between(min, max))
                        .orderBy(user.userId.asc())
        );
        reader.setSaveState(false);
        return reader;
    }


    @Bean
    public ItemProcessor<User, User> processor() {
        log.info("********** This is " + JOB_NAME + "_processor");
        return new SaveOrUpdateBlogItemProcessor();
    }

    @Bean
    public SingleTxJpaItemWriter<User> writer() {
        log.info("********** This is " + JOB_NAME + "_writer");
        SingleTxJpaItemWriter<User> writer = new SingleTxJpaItemWriter<>();
        writer.setEntityManager(entityManager);
        return writer;
    }
}
