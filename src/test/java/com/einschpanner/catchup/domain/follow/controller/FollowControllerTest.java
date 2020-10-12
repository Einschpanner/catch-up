package com.einschpanner.catchup.domain.follow.controller;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FollowControllerTest extends ApiDocumentationTest {

    // TODO : 블로그 기능 개발 이후
    @Test
    void findMyFollowingBlog() throws Exception {
    }

    @Test
    @WithMockUser
    void toggleFollow() throws Exception {
        int subscribeId = 1;

        // When & Then
        mockMvc.perform(post("/following/" + subscribeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("toggle-follow"))
        ;
    }

    @Test
    @WithMockUser
    void findFollowing() throws Exception {

        User user1 = User.builder().userId(1L).build();
        User user2 = User.builder().userId(2L).build();
        User user3 = User.builder().userId(3L).build();

        // Given
        List<Follow> follows = new ArrayList<>();
        Follow follow1 = Follow.builder()
                .follower(user1)
                .following(user2)
                .build();
        follows.add(follow1);

        Follow follow2 = Follow.builder()
                .follower(user1)
                .following(user3)
                .build();
        follows.add(follow2);

        given(followService.findAllFollowing(any(Long.class)))
                .willReturn(follows);

        int subscribeId = 1;
        // When & Then
        mockMvc.perform(get("/following/" + subscribeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-following"))
        ;
    }

    @Test
    @WithMockUser
    void findFollower() throws Exception {
        User user1 = User.builder().userId(1L).build();
        User user2 = User.builder().userId(2L).build();
        User user3 = User.builder().userId(3L).build();

        // Given
        List<Follow> follows = new ArrayList<>();
        Follow follow1 = Follow.builder()
                .follower(user2)
                .following(user1)
                .build();
        follows.add(follow1);

        Follow follow2 = Follow.builder()
                .follower(user3)
                .following(user1)
                .build();
        follows.add(follow2);

        given(followService.findAllFollower(any(Long.class)))
                .willReturn(follows);

        int subscribeId = 1;
        // When & Then
        mockMvc.perform(get("/following/" + subscribeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-follower"))
        ;
    }
}