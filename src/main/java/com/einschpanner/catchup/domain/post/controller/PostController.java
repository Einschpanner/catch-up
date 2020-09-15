package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.PostDto;
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
    public PostDto.Response savePost(
            @RequestBody PostDto.CreateRequest dto
    ){
        Post post = postService.save(dto);
        return new PostDto.Response(post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto.Response> findAllPosts(){
        return postService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto.Response findPost(
            @PathVariable final Long id
    ){
        Post post = postService.findById(id);
        return new PostDto.Response(post);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto.Response updatePost(
            @PathVariable final Long id,
            @RequestBody PostDto.UpdateRequest dto
    ){
        Post post = postService.update(id, dto);
        return new PostDto.Response(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(
            @PathVariable final Long id
    ){
        postService.delete(id);
    }
}
