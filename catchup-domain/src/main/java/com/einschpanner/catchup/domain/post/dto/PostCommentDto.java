package com.einschpanner.catchup.domain.post.dto;

import com.einschpanner.catchup.domain.post.domain.PostComment;
import lombok.*;

public class PostCommentDto {

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class Req {
        private Long postId;
        private Long parentsId;
        private String email;
        private String contents;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Res {
        private String email;
        private String contents;

        @Builder
        public Res(PostComment postComment) {
            this.email = postComment.getEmail();
            this.contents = postComment.getContents();
        }
    }

}
