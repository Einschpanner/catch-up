package com.einschpanner.catchup.domain.post.repository;

import com.einschpanner.catchup.domain.post.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
