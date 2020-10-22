package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends ApiDocumentationTest {

    @Test
    @WithMockCustomUser
    void savePost() throws Exception {

        // Given
        PostDto.CreateReq postDto = new PostDto.CreateReq();
        postDto.setDescription("test description");
        postDto.setEmail("test@naver.com");
        postDto.setTitle("Test Title");
        postDto.setUrlThumbnail("Test Thumbnail");
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
}