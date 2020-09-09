//package com.einschpanner.catchup.global.security.controller;
//
//import com.einschpanner.catchup.hello.dao.HelloRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class TestControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private HelloRepository helloRepository;
//
//    @Test
//    @WithMockUser
//    public void 권한정보_리턴() throws Exception {
//
//        mockMvc.perform(get("/auth"))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//}