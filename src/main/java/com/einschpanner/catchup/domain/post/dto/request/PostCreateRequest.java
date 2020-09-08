package com.einschpanner.catchup.domain.post.dto.request;

import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostCreateRequest {

    private String title;
    private String description;
    private String email;
    private String urlThumbnail;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .description(description)
                .email(email)
                .urlThumbnail(urlThumbnail)
                .cntComment(0)
                .cntLike(0)
                .isDeleted(false)
                .build();
    }
}
