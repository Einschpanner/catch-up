package com.einschpanner.catchup.domain.post.repository;

import com.einschpanner.catchup.domain.post.domain.PostTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.einschpanner.catchup.domain.post.domain.QPostTag.postTag;

@RequiredArgsConstructor
@Repository
public class PostTagQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PostTag exists(Long postId, String tagName) {

        return queryFactory
                .selectFrom(postTag)
                .where(postTag.post.postId.eq(postId))
                .where(postTag.tag.tagName.eq(tagName))
                .fetchOne();
    }


    public List<PostTag> findAllByPostId(Long postId) {

        return queryFactory
                .selectFrom(postTag)
                .where(postTag.post.postId.eq(postId))
                .fetch();
    }
}
