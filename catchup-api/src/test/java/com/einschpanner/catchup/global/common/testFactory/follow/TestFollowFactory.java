package com.einschpanner.catchup.global.common.testFactory.follow;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.user.domain.User;

public class TestFollowFactory {

    public static Follow createFollow(Long followId, User follower, User following ){
        return Follow.builder()
                .followId(followId)
                .follower(follower)
                .following(following)
                .build();
    }
}
