package com.einschpanner.catchup.domain.tag.dao;

import com.einschpanner.catchup.domain.tag.domain.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.einschpanner.catchup.domain.tag.domain.QTag.tag;

@RequiredArgsConstructor
@Repository
public class TagQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Tag exists(String tagName) {

        return queryFactory
                .selectFrom(tag)
                .where(tag.tagName.eq(tagName))
                .fetchOne();
    }
}
