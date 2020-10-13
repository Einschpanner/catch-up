package com.einschpanner.catchup.domain.follow.controller;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FollowControllerTest extends ApiDocumentationTest {

    private static User user1;
    private static User user2;
    private static User user3;

    @BeforeAll
    public void setUp() {
        user1 = User.builder()
                .userId(1L)
                .nickname("woowon")
                .email("wwlee94@naver.com")
                .urlProfile("https://google.com")
                .build();
        user2 = User.builder()
                .userId(2L)
                .nickname("jinyoung")
                .email("rlawlsdud419@gmail.com")
                .urlProfile("https://google.com")
                .build();
        user3 = User.builder()
                .userId(3L)
                .nickname("TEST")
                .email("test@gmail.com")
                .urlProfile("https://kakao.com")
                .build();
    }

    // TODO : 블로그 기능 개발 이후
    @Test
    void findMyFollowingBlog() throws Exception {
    }

    @Test
    @WithMockUser
    void toggleFollow() throws Exception {
        int subscribeId = 1;

        // When & Then
        mockMvc.perform(post("/following/{subscribeId}", subscribeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("toggle-follow",
                        pathParameters(
                                parameterWithName("subscribeId").description("팔로잉할 USER ID")
                        )))
        ;
    }

    @Test
    @WithMockUser
    void findFollowing() throws Exception {

        // Given
        List<Follow> follows = new ArrayList<>();
        Follow follow1 = Follow.builder()
                .followId(1L)
                .follower(user1)
                .following(user2)
                .build();
        follows.add(follow1);

        Follow follow2 = Follow.builder()
                .followId(2L)
                .follower(user1)
                .following(user3)
                .build();
        follows.add(follow2);

        given(followService.findAllFollowing(any(Long.class)))
                .willReturn(follows);

        int subscribeId = 1;
        // When & Then
        mockMvc.perform(get("/following/{subscribeId}", subscribeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-following",
                        pathParameters(
                                parameterWithName("subscribeId").description("USER ID")
                        )))
        ;
    }

    @Test
    @WithMockUser
    void findFollower() throws Exception {

        // Given
        List<Follow> follows = new ArrayList<>();
        Follow follow1 = Follow.builder()
                .followId(1L)
                .follower(user2)
                .following(user1)
                .build();
        follows.add(follow1);

        Follow follow2 = Follow.builder()
                .followId(2L)
                .follower(user3)
                .following(user1)
                .build();
        follows.add(follow2);

        given(followService.findAllFollower(any(Long.class)))
                .willReturn(follows);

        int subscribeId = 1;
        // When & Then
        mockMvc.perform(get("/follower/{subscribeId}", subscribeId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-follower",
                        pathParameters(
                                parameterWithName("subscribeId").description("USER ID")
                        )))
        ;
    }
}