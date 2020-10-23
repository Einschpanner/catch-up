package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import com.einschpanner.catchup.global.common.testFactory.post.TestPostFactory;
import com.einschpanner.catchup.global.common.testFactory.user.TestUserFactory;
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
class PostLikeControllerTest extends ApiDocumentationTest {

    private static User user1;
    private static User user2;

    @BeforeAll
    public void setUp() {
        List<User> users = TestUserFactory.getTestUsers();
        user1 = users.get(0);
        user2 = users.get(1);
    }

    @Test
    @WithMockCustomUser
    void togglePostLike() throws Exception {
        Long postId = 1L;

        // When & Then
        mockMvc.perform(post("/posts/{postId}/likes", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("toggle-follow",
                        pathParameters(
                            parameterWithName("postId").description("포스트 ID")
                        )
                ))
        ;
    }

    @Test
    @WithMockUser
    void findAllPostLikes()throws Exception {

        Post post = TestPostFactory.createPost(1L, user1, null);

        // Given
        List<PostLike> postLikes = new ArrayList<>();
        PostLike postLike1 = PostLike.of(post, user1);
        postLikes.add(postLike1);
        PostLike postLike2 = PostLike.of(post, user2);
        postLikes.add(postLike2);

        given(postLikeService.findAllByPostId(any(Long.class)))
                .willReturn(postLikes);

        Long postId = 1L;
        // When & Then
        mockMvc.perform(get("/posts/{postId}/likes", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-post-likes",
                        pathParameters(
                                parameterWithName("postId").description("포스트 ID")
                        ))
                )
        ;
    }
}