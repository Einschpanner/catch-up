package com.einschpanner.catchup.domain.post.dto.request;

import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private String title;
    private String description;
    private String email;
    private String urlThumbnail;
    private int cntComment;
    private int cntLike;
    private boolean isDeleted;

    public Post toEntity(Long postId) {
        return Post.builder()
                .postId(postId)
                .title(title)
                .description(description)
                .email(email)
                .urlThumbnail(urlThumbnail)
                .cntComment(cntComment)
                .cntLike(cntLike)
                .isDeleted(isDeleted)
                .build();
    }
}
