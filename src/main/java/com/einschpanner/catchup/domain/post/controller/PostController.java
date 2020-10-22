package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
import com.einschpanner.catchup.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        Post post = postService.save(userId, dto);
        return new PostDto.Res(post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto.Res> findAllPosts() {
        List<Post> posts = postService.findAll();
        return posts.stream()
                .map(PostDto.Res::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto.ResWithUser findPost(
            @PathVariable final Long postId
    ) {
        Post post = postService.findById(postId);
        return new PostDto.ResWithUser(post);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto.Res updatePost(
            @PathVariable final Long postId,
            @RequestBody PostDto.UpdateReq dto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        Post post = postService.update(userId, postId, dto);
        return new PostDto.Res(post);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(
            @PathVariable final Long postId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        postService.delete(userId, postId);
    }
}
