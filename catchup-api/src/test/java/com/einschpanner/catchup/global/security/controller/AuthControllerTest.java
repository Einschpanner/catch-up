package com.einschpanner.catchup.global.security.controller;

import com.einschpanner.catchup.global.common.ApiDocumentationTest;
import com.einschpanner.catchup.global.common.WithMockCustomUser;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ApiDocumentationTest {


    @Test
    @WithMockCustomUser
    public void 권한정보_리턴() throws Exception {

        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("auth"))
        ;
    }

}