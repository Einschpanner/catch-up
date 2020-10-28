package com.einschpanner.catchup;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @EnableBatchProcessing 선언을 통하여 BatchJob의 구현부에서 필요한 Job과 Step객체를 생성해주는 JobBuilderFactory와 StepBuilderFatory를 자동으로 주입받을 수 있음
 */

@EnableBatchProcessing
@SpringBootApplication
public class CatchUpBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatchUpBatchApplication.class, args);
    }
}
