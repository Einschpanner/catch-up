package com.einschpanner.catchup.domain.post.dto;

import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.*;

public class PostDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class CreateRequest {

        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateRequest {

        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
        private int cntComment;
        private int cntLike;
        private boolean isDeleted;
    }

    @Getter
    public static class Response {

        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
        private int cntLike;
        private int cntComment;

        public Response(Post post){
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.email = post.getEmail();
            this.urlThumbnail = post.getUrlThumbnail();
            this.cntLike = post.getCntLike();
            this.cntComment = post.getCntComment();
        }
    }
}
