package com.einschpanner.catchup.domain.follow.dto;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.domain.user.dto.UserDto;
import lombok.Getter;

import java.util.List;

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

    @Getter
    public static class BlogRes {

        private Long followId;
        private UserDto.Res user;
        private List<Blog> blogs;

        public BlogRes(Follow follow){
            User user = follow.getFollowing();
            this.followId = follow.getFollowId();
            this.user = new UserDto.Res(user);
            this.blogs = user.getBlogs();
        }
    }
}
