package com.einschpanner.catchup.domain.post.repository;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.einschpanner.catchup.domain.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * TEST Query
     */
    public List<Post> findByTitle(String title) {
        return queryFactory.selectFrom(post)
                .where(post.title.contains(title))
                .fetch();
    }
}
