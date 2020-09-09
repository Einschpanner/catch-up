package com.einschpanner.catchup.domain.post.dto.response;

import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    private String title;
    private String description;
    private String email;
    private String urlThumbnail;
    private int cntLike;
    private int cntComment;

    public PostResponse(Post post){
       this.title = post.getTitle();
       this.description = post.getDescription();
       this.email = post.getEmail();
       this.urlThumbnail = post.getUrlThumbnail();
       this.cntLike = post.getCntLike();
       this.cntComment = post.getCntComment();
    }
}
