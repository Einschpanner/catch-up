package com.einschpanner.catchup.global.common.testFactory.post;

import com.einschpanner.catchup.domain.post.dto.PostCommentDto;

public class TestPostCommentFactory {

    public static PostCommentDto.Req createPostCommentDto() {
        return PostCommentDto.Req.builder()
                .postId((long) 1)
                .parentsId((long) 2)
                .email("rlawlsdud419@gmail.com")
                .contents("test contents")
                .build();
    }
}
