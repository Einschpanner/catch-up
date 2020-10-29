package com.einschpanner.catchup.global.common.testFactory.user;

import com.einschpanner.catchup.domain.blog.domain.Blog;
import com.einschpanner.catchup.domain.user.domain.AuthProvider;
import com.einschpanner.catchup.domain.user.domain.Role;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.domain.user.dto.ProfileDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUserFactory {

    public static List<User> getTestUsers(){
        User user1 = User.builder()
                .userId(1L)
                .nickname("woowon")
                .email("wwlee94@naver.com")
                .urlProfile("https://google.com")
                .build();
        User user2 = User.builder()
                .userId(2L)
                .nickname("jinyoung")
                .email("rlawlsdud419@gmail.com")
                .urlProfile("https://google.com")
                .build();
        User user3 = User.builder()
                .userId(3L)
                .nickname("TEST")
                .email("test@gmail.com")
                .urlProfile("https://kakao.com")
                .build();

        return new ArrayList<>(
                Arrays.asList(user1, user2, user3)
        );
    }

    public static User createUser(Long userId, List<Blog> blogs){
        return User.builder()
                .userId(userId)
                .nickname("Test Name")
                .email("Test Email")
                .urlProfile("https://url.link")
                .addrBlog("https://blog.link")
                .addrGithub("https://github.com")
                .provider(AuthProvider.google)
                .blogs(blogs)
                .role(Role.USER)
                .cntFollower(0)
                .cntFollowing(0)
                .build();
    }

    public static ProfileDto.UpdateReq createProfileDto(){
        return ProfileDto.UpdateReq.builder()
                .nickname("testNick")
                .urlProfile("test Url")
                .description("description")
                .addrRss("rss")
                .addrGithub("github")
                .addrBlog("blog")
                .build();
    }
}