package com.einschpanner.catchup.domain.user.api;

import com.einschpanner.catchup.domain.user.dto.ProfileDto;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ApiDocumentationTest {

    @Test
    @WithMockCustomUser
    void saveProfile() throws Exception {
        // Given
        ProfileDto.UpdateReq req = ProfileDto.UpdateReq.builder()
                .nickname("testNick")
                .urlProfile("test Url")
                .description("description")
                .addrRss("rss")
                .addrGithub("github")
                .addrBlog("blog")
                .build();

        // When & Then
        mockMvc.perform(post("/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-profiles",
                        requestFields(
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("urlProfile").type(JsonFieldType.STRING).description("프로필 사진 위치"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("소개글"),
                                fieldWithPath("addrRss").type(JsonFieldType.STRING).description("RSS 주소"),
                                fieldWithPath("addrGithub").type(JsonFieldType.STRING).description("깃허브 주소"),
                                fieldWithPath("addrBlog").type(JsonFieldType.STRING).description("블로그 주소")
                        )
                ))
        ;
    }

    @Test
    void getProfile() throws Exception {
        // When & Then
        mockMvc.perform(get("/profiles/{userId}", (long) 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-profile",
                        pathParameters(
                                parameterWithName("userId").description("USER ID")
                        )))
        ;
    }
}