package com.einschpanner.catchup.domain.follow.repository;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.einschpanner.catchup.domain.follow.domain.QFollow.follow;

@RequiredArgsConstructor
@Repository
public class FollowQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Follow exists(Long followerId, Long followingId) {

        return queryFactory
                .selectFrom(follow)
                .where(follow.follower.userId.eq(followerId))
                .where(follow.following.userId.eq(followingId))
                .fetchOne();
    }

    public List<Follow> findAllFollowingByFollowerId(Long followerId){

        return queryFactory
                .selectFrom(follow)
                .where(follow.follower.userId.eq(followerId))
                .fetch();
    }

    public List<Follow> findAllFollowerByFollowingId(Long followingId){

        return queryFactory
                .selectFrom(follow)
                .where(follow.following.userId.eq(followingId))
                .fetch();
    }
}