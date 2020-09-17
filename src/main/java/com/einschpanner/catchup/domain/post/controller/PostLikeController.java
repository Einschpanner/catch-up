package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.post.dto.PostLikeDto;
import com.einschpanner.catchup.domain.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostLikeDto.Response savePostLike(
            @RequestBody PostLikeDto.CreateRequest dto
    ){
        PostLike postLike = postLikeService.save(dto);
        return new PostLikeDto.Response(postLike);
    }


    // ?? PostController에 들어가야 하나?
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto.Response> findAllPostLikes(
            PostLikeDto.FindByUserRequest dto
    ){
        List<PostLike> postLikes = postLikeService.findAllByUserId(dto);
        return postLikes.stream()
                .map(PostLike::getPost)
                .map(PostDto.Response::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void cancelPostLike(
            @RequestBody PostLikeDto.CancelRequest dto
    ){
        postLikeService.delete(dto);
    }
}
