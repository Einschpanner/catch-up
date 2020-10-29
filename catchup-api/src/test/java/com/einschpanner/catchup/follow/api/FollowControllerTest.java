package com.einschpanner.catchup.follow.api;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import com.einschpanner.catchup.global.common.testFactory.follow.TestFollowFactory;
import com.einschpanner.catchup.global.common.testFactory.user.TestUserFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<User> users = TestUserFactory.getTestUsers();
        user1 = users.get(0);
        user2 = users.get(1);
        user3 = users.get(2);
    }

    // TODO : 블로그 기능 개발 이후
    @Test
    void findMyFollowingBlog() throws Exception {
    }

    @Test
    @WithMockCustomUser
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
        Follow follow1 = TestFollowFactory.createFollow(1L, user1, user2);
        Follow follow2 = TestFollowFactory.createFollow(2L, user1, user3);

        List<Follow> follows = new ArrayList<>(
                Arrays.asList(follow1, follow2)
        );

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
        Follow follow1 = TestFollowFactory.createFollow(1L, user2, user1);
        Follow follow2 = TestFollowFactory.createFollow(2L, user3, user1);

        List<Follow> follows = new ArrayList<>(
                Arrays.asList(follow1, follow2)
        );

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