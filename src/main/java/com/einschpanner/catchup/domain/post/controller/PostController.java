package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.request.PostCreateRequest;
import com.einschpanner.catchup.domain.post.dto.request.PostUpdateRequest;
import com.einschpanner.catchup.domain.post.dto.response.PostResponse;
import com.einschpanner.catchup.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse savePost(@RequestBody PostCreateRequest postCreateRequest){
        Post post = postService.save(postCreateRequest);
        return new PostResponse(post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> findAllPosts(){
        return postService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse findPost(@PathVariable final Long id){
        Post post = postService.findById(id);
        return new PostResponse(post);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    // Long wrapper 타입으로 받아도 문제 없는 것?!
    public PostResponse updatePost(@PathVariable final Long id, @RequestBody PostUpdateRequest postUpdateRequest){
        Post post = postService.update(id, postUpdateRequest);
        return new PostResponse(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable final Long id){
        postService.delete(id);
    }
}
