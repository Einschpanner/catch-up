package com.einschpanner.catchup.domain.post.repository;

import com.einschpanner.catchup.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndUser_UserId(Long postId, Long userId);
}
