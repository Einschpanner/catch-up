package com.einschpanner.catchup.domain.follow.dto;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.user.dto.UserDto;
import lombok.Getter;

public class FollowDto {

    @Getter
    public static class FollowingRes {

        private Long followId;
        private UserDto.Res user;

        public FollowingRes(Follow follow){
            this.followId = follow.getFollowId();
            this.user = new UserDto.Res(follow.getFollowing());
        }
    }

    @Getter
    public static class FollowerRes {

        private Long followId;
        private UserDto.Res user;

        public FollowerRes(Follow follow){
            this.followId = follow.getFollowId();
            this.user = new UserDto.Res(follow.getFollower());
        }
    }
}
