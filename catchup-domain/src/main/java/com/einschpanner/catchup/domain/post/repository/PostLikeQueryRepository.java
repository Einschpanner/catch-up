package com.einschpanner.catchup.domain.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.einschpanner.catchup.domain.post.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.einschpanner.catchup.domain.post.domain.QPostLike.postLike;
import static com.einschpanner.catchup.domain.post.domain.QPost.post;


/**
 * https://jojoldu.tistory.com/516
 * JPA exists 성능 개선
 */

@RequiredArgsConstructor
@Repository
public class PostLikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostLike exists(Long postId, Long userId) {

        return queryFactory
                .selectFrom(postLike)
                .where(postLike.post.postId.eq(postId))
                .where(postLike.user.userId.eq(userId))
                .fetchOne();
    }

    // 임시
    public List<PostLike> findAllByPostId(Long postId) {
        return queryFactory
                .selectFrom(postLike)
                .where(postLike.post.postId.eq(postId))
                .fetch();
    }
}