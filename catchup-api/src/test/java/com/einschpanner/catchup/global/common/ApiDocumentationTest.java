package com.einschpanner.catchup.global.common;

import com.einschpanner.catchup.domain.post.dao.PostQueryRepository;
import com.einschpanner.catchup.domain.post.dao.PostTagQueryRepository;
import com.einschpanner.catchup.follow.api.FollowController;
import com.einschpanner.catchup.follow.service.FollowService;
import com.einschpanner.catchup.global.common.config.TestConfig;
import com.einschpanner.catchup.global.security.config.SecurityConfig;
import com.einschpanner.catchup.global.security.controller.AuthController;
import com.einschpanner.catchup.post.api.PostCommentController;
import com.einschpanner.catchup.post.api.PostController;
import com.einschpanner.catchup.post.api.PostLikeController;
import com.einschpanner.catchup.post.api.PostTagController;
import com.einschpanner.catchup.post.service.PostCommentService;
import com.einschpanner.catchup.post.service.PostLikeService;
import com.einschpanner.catchup.post.service.PostService;
import com.einschpanner.catchup.post.service.PostTagService;
import com.einschpanner.catchup.user.api.UserController;
import com.einschpanner.catchup.user.service.UserService;
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
        PostController.class,
        PostLikeController.class,
        FollowController.class,
        PostCommentController.class,
        UserController.class,
        PostTagController.class
},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureRestDocs
@Import(TestConfig.class)
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

    @MockBean
    protected PostLikeService postLikeService;

    @MockBean
    protected FollowService followService;

    @MockBean
    protected PostCommentService postCommentService;
  
    @MockBean
    protected UserService userService;

    @MockBean
    protected PostTagService postTagService;

    @MockBean
    protected PostTagQueryRepository postTagQueryRepository;
}