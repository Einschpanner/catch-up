package com.einschpanner.catchup.domain.tag.dao;

import com.einschpanner.catchup.domain.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
