package com.einschpanner.catchup.hello.dao;

import com.einschpanner.catchup.hello.domain.Hello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository extends JpaRepository<Hello, Long> {
}
