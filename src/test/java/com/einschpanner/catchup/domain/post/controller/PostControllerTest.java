package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import com.einschpanner.catchup.global.common.testFactory.post.TestPostFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends ApiDocumentationTest {

    @Test
    @WithMockCustomUser
    void savePost() throws Exception {

        // Given
        PostDto.CreateReq postDto = TestPostFactory.createPostDto();
        Post post = this.modelMapper.map(postDto, Post.class);

        given(postService.save(any(Long.class), any(PostDto.CreateReq.class)))
                .willReturn(post);

        // When & Then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-post",
                        requestFields(
                                fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("urlThumbnail").type(JsonFieldType.STRING).description("사진위치")
                        )
                ))
        ;
    }

    @Test
    void findAllPosts() throws Exception {
        // Given
        List<Post> posts = TestPostFactory.findAllPosts();

        given(postService.findAll())
                .willReturn(posts);

        // When & Then
        mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-all-post"))
        ;
    }

    @Test
    void findPost() throws Exception {
        // Given
        Post post = TestPostFactory.findPost();

        given(postService.findById(any(Long.class)))
                .willReturn(post);

        Long postId = 1L;
        // When & Then
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-post",
                        pathParameters(
                            parameterWithName("postId").description("포스트 ID")
                        )
                ))
        ;
    }

//    @Test
//    @WithMockCustomUser
//    void updatePost() throws Exception {
//        // Given
//        PostDto.UpdateReq postDto = TestPostFactory.updatePostDto();
//        System.out.println(postDto);
//
//        Post post = this.modelMapper.map(postDto, Post.class);
//        System.out.println(post);
//
//        given(postService.update(any(Long.class), any(Long.class), any(PostDto.UpdateReq.class)))
//                .willReturn(post);
//
//        // When & Then
//        mockMvc.perform(put("/posts/{postId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("utf-8")
//                .content(objectMapper.writeValueAsString(postDto))) // ?? 왜 "deleted":false 로 요청이 되는지?
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("update-post",
//                        requestFields(
//                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
//                                fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
//                                fieldWithPath("urlThumbnail").type(JsonFieldType.STRING).description("사진위치"),
//                                fieldWithPath("cntComment").type(JsonFieldType.NUMBER).description("댓글 개수"),
//                                fieldWithPath("cntLike").type(JsonFieldType.NUMBER).description("좋아요 개수"),
//                                fieldWithPath("isDeleted").type(JsonFieldType.BOOLEAN).description("포스트 삭제 여부")
//                        )
//                ))
//        ;
//    }

    @Test
    @WithMockCustomUser
    void deletePost() throws Exception {
        Long postId = 1L;
        // When & Then
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-post",
                        pathParameters(
                                parameterWithName("postId").description("포스트 ID")
                        )
                ))
        ;
    }
}