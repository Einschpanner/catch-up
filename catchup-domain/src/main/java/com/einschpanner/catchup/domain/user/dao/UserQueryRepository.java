package com.einschpanner.catchup.domain.user.dao;

import com.einschpanner.catchup.domain.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.einschpanner.catchup.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<User> findAllByExistsBlogRss() {

        return queryFactory.selectFrom(user)
                .where(user.addrRss.isNotNull())
                .fetch();
    }
}
