package com.einschpanner.catchup.domain.post.dto.response;

import com.einschpanner.catchup.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.modelmapper.ModelMapper;

@Getter
@AllArgsConstructor
@Builder
public class PostListResponse {

    private String title;
    private String description;
    private String email;
    private String urlThumbnail;
    private int cntLike;
    private int cntComment;

    public static PostListResponse of(Post post){
//        ModelMapper modelMapper = new ModelMapper();
//        return modelMapper.map(post, PostListResponse.class);
        return PostListResponse.builder()
                .title(post.getTitle())
                .description(post.getDescription())
                .email(post.getEmail())
                .urlThumbnail(post.getUrlThumbnail())
                .cntLike(post.getCntLike())
                .cntComment(post.getCntComment())
                .build();
    }
}
