package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends ApiDocumentationTest {

    @Test
    void savePost() throws Exception {
        // Given
        PostDto.CreateRequest postDto = new PostDto.CreateRequest();
        postDto.setDescription("test description");
        postDto.setEmail("test@naver.com");
        postDto.setTitle("Test Title");
        postDto.setUrlThumbnail("Test Thumbnail");

        // When & Then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(postDto)))
                .andDo(print())
                .andExpect(status().isOk())
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