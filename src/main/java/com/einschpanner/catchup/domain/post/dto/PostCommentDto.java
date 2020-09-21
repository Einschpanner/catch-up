package com.einschpanner.catchup.domain.post.dto;

import com.einschpanner.catchup.domain.post.domain.PostComment;
import lombok.*;

public class PostCommentDto {

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Req {
        private Long post_id;
        private Long parents_id;
        private String email;
        private String contents;

        @Builder
        public Req(Long post_id, Long parents_id, String email, String contents) {
            this.post_id = post_id;
            this.parents_id = parents_id;
            this.email = email;
            this.contents = contents;
        }
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
