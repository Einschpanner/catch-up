package com.einschpanner.catchup.post.api;

import com.einschpanner.catchup.domain.post.domain.PostTag;
import com.einschpanner.catchup.domain.post.dto.PostTagDto;
import com.einschpanner.catchup.domain.tag.dto.TagDto;
import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import com.einschpanner.catchup.global.common.testFactory.post.TestPostTagFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

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

class PostTagControllerTest extends ApiDocumentationTest {

    @Test
    @WithMockCustomUser
    void savePostTag() throws Exception {
        // Given
        PostTagDto.Req req = TestPostTagFactory.createPostTagDto();

        // When & Then
        mockMvc.perform(post("/posts/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-postTag",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("postTags").type(JsonFieldType.ARRAY).description("포스트 태그 리스트")
                        )
                ))
        ;
    }

    @Test
    void getPostTagList() throws Exception {
        // Given
        List<PostTag> postTagList = TestPostTagFactory.createPostTagList();
        TagDto.Res res = new TagDto.Res(postTagList);

        given(postTagService.getPostTagList((long) 1))
                .willReturn(res);

        // When & Then
        mockMvc.perform(get("/posts/{postId}/tags", (long) 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-postTagList",
                        pathParameters(
                                parameterWithName("postId").description("포스트 ID")
                        )))
        ;

    }

    @Test
    @WithMockCustomUser
    void deletePostTag() throws Exception {
        // Given
        PostTagDto.Req req = TestPostTagFactory.createPostTagDto();

        // When & Then
        mockMvc.perform(delete("/posts/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("delete-postTag",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("포스트 아이디"),
                                fieldWithPath("postTags").type(JsonFieldType.ARRAY).description("포스트 태그 리스트")
                        )))
        ;
    }
}