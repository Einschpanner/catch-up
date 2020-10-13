package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto.Res savePost(
            @RequestBody PostDto.CreateReq dto
    ) {
        Post post = postService.save(dto);
        return new PostDto.Res(post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto.Res> findAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto.Res findPost(
            @PathVariable final Long postId
    ) {
        Post post = postService.findById(postId);
        return new PostDto.Res(post);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto.Res updatePost(
            @PathVariable final Long postId,
            @RequestBody PostDto.UpdateReq dto
    ) {
        Post post = postService.update(postId, dto);
        return new PostDto.Res(post);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(
            @PathVariable final Long postId
    ) {
        postService.delete(postId);
    }
}
