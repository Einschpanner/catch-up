package com.einschpanner.catchup.global.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableJpaAuditing
public class Config {

    /**
     * QueryDSL 설정
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        /* 연결 전략 : 같은 타입 + 필드명이 같은 경우만 동작 */
        modelMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
