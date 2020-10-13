package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.PostComment;
import com.einschpanner.catchup.domain.post.dto.PostCommentDto;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.testFactory.post.TestPostCommentFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostCommentControllerTest extends ApiDocumentationTest {

    @Test
    @WithMockUser
    void savePostComment() throws Exception {

        // Given
        PostCommentDto.Req req = TestPostCommentFactory.createPostCommentDto();

        // When & Then
        mockMvc.perform(post("/posts/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-postComment",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("parentsId").type(JsonFieldType.NUMBER).description("부모 댓글 아이디"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("내")
                        )
                ))
        ;
    }

    @Test
    void getPostCommentList() throws Exception {
        // Given
        PostCommentDto.Req req = TestPostCommentFactory.createPostCommentDto();

        List<PostComment> postCommentList = new ArrayList<PostComment>();
        postCommentList.add(modelMapper.map(req, PostComment.class));
        given(postCommentService.getPostCommentList((long) 1))
                .willReturn(postCommentList);

        // When & Then
        mockMvc.perform(get("/posts/{postId}/comments", (long) 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-postComments",
                        pathParameters(
                                parameterWithName("postId").description("포스트 ID")
                        )))
        ;
    }

    @Test
    void getPostCommentReplyList() throws Exception {
        // Given
        PostCommentDto.Req req = PostCommentDto.Req.builder()
                .postId((long) 1)
                .parentsId((long) 2)
                .email("rlawlsdud419@gmail.com")
                .contents("test contents")
                .build();
        List<PostComment> postCommentList = new ArrayList<PostComment>();
        postCommentList.add(modelMapper.map(req, PostComment.class));
        given(postCommentService.getPostCommentReplyList((long) 2))
                .willReturn(postCommentList);

        // When & Then
        mockMvc.perform(get("/posts/comments/{commentId}/replies", (long) 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-postCommentReplies",
                        pathParameters(
                                parameterWithName("commentId").description("부모 댓글 ID")
                        )))
        ;
    }

    @Test
    @WithMockUser
    void deletePostComment() throws Exception {
        // When & Then
        mockMvc.perform(delete("/posts/comments/{commentId}", (long) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-postComment",
                        pathParameters(
                                parameterWithName("commentId").description("부모 댓글 ID")
                        )))
        ;
    }
}