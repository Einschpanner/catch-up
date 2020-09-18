package com.einschpanner.catchup.global.common;

import com.einschpanner.catchup.domain.post.controller.PostController;
import com.einschpanner.catchup.domain.post.repository.PostQueryRepository;
import com.einschpanner.catchup.domain.post.service.PostService;
import com.einschpanner.catchup.global.security.config.SecurityConfig;
import com.einschpanner.catchup.global.security.controller.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {
        AuthController.class,
        PostController.class
},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public abstract class ApiDocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;

    @MockBean
    protected PostService postService;

    @MockBean
    protected PostQueryRepository postQueryRepository;


}