package com.einschpanner.catchup.global.security.controller;

import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ApiDocumentationTest {


    @Test
    @WithMockUser
    public void 권한정보_리턴() throws Exception {

        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("auth"))
        ;
    }

}