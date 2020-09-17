package com.einschpanner.catchup.domain.post.dto;


import com.einschpanner.catchup.domain.post.domain.PostLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostLikeDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateRequest { // 네이밍 의논
        private Long postId;
        private Long userId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CancelRequest {
        private Long postId;
        private Long userId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FindByUserRequest {
        private Long userId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FindByPostRequest {
        private Long postId;
    }

    @Getter
    public static class Response {
        private Long postLikeId;
        private Long postId;
        private Long userId;

        public Response(PostLike postLike){
            this.postLikeId = postLike.getPostLikeId();
            this.postId = postLike.getPost().getPostId();
            this.userId = postLike.getUser().getUserId();
        }
    }
}
