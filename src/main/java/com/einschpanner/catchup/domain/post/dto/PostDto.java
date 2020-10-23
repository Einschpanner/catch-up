package com.einschpanner.catchup.domain.post.dto;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.domain.user.dto.UserDto;
import lombok.*;

public class PostDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateReq {

        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class UpdateReq {

        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
        private int cntComment;
        private int cntLike;
        private Boolean isDeleted;
    }

    @Getter
    public static class Res {

        private Long id;
        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
        private int cntLike;
        private int cntComment;

        public Res(Post post) {
            this.id = post.getPostId();
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.email = post.getEmail();
            this.urlThumbnail = post.getUrlThumbnail();
            this.cntLike = post.getCntLike();
            this.cntComment = post.getCntComment();
        }
    }

    @Getter
    public static class ResWithUser {

        private Long id;
        private String title;
        private String description;
        private String email;
        private String urlThumbnail;
        private int cntLike;
        private int cntComment;
        private UserDto.Res user;

        public ResWithUser(Post post) {
            this.id = post.getPostId();
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.email = post.getEmail();
            this.urlThumbnail = post.getUrlThumbnail();
            this.cntLike = post.getCntLike();
            this.cntComment = post.getCntComment();
            this.user = new UserDto.Res(post.getUser());
        }
    }
}
