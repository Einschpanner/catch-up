package com.einschpanner.catchup.domain.post.repository;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostComment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.einschpanner.catchup.domain.post.domain.QPost.post;
import static com.einschpanner.catchup.domain.post.domain.QPostComment.postComment;

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

    public List<PostComment> findAllByPostAndParents(Long postId, Long commentId) {

        return queryFactory.selectFrom(postComment)
                .where(eqPostComment(commentId),
                        eqPostId(postId))
                .fetch();
    }

    private BooleanExpression eqPostId(Long postId) {
        if (StringUtils.isEmpty(postId)) {
            return null;
        }
        return postComment.post.postId.eq(postId);
    }

    private BooleanExpression eqPostComment(Long commentId) {
        if (StringUtils.isEmpty(commentId)) {
            return postComment.parents.isNull();
        }
        return postComment.parents.commentId.eq(commentId);
    }

}
