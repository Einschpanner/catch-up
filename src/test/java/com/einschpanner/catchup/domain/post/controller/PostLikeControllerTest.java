package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.domain.PostLike;
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
    }

//             TODO: 진영 PR 머지 이후 userId 가져와서 테스팅!
//    @Test
//    @WithMockUser
//    void togglePostLike() throws Exception {
//        int postId = 1;
//
//        // When & Then
//        mockMvc.perform(post("/posts/" + postId + "/likes")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("utf-8"))
//                .andDo(print())
//                .andExpect(status().isNoContent())
//                .andDo(document("toggle-follow"))
//        ;
//    }

    @Test
    @WithMockUser
    void findAllPostLikes()throws Exception {

        Post post1 = Post.builder()
                .postId(1L)
                .title("첫번째 포스팅")
                .cntComment(0)
                .cntLike(0)
                .user(user1)
                .build();

        // Given
        List<PostLike> postLikes = new ArrayList<>();
        PostLike postLike1 = PostLike.of(post1, user1);
        postLikes.add(postLike1);
        PostLike postLike2 = PostLike.of(post1, user2);
        postLikes.add(postLike2);

        given(postLikeService.findAllByPostId(any(Long.class)))
                .willReturn(postLikes);

        int postId = 1;
        // When & Then
        mockMvc.perform(get("/posts/{postId}/likes", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-post-likes",
                        pathParameters(
                                parameterWithName("postId").description("POST ID")
                        )))
        ;
    }
}