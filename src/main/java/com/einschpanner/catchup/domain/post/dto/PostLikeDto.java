package com.einschpanner.catchup.domain.post.dto;


import com.einschpanner.catchup.domain.post.domain.PostLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostLikeDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private Long userId;
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
