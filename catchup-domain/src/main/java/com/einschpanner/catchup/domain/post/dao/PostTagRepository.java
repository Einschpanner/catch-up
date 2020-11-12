package com.einschpanner.catchup.domain.post.dao;

import com.einschpanner.catchup.domain.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
