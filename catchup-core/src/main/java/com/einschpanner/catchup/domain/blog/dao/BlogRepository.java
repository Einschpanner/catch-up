package com.einschpanner.catchup.domain.blog.dao;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
