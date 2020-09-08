package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.Post;
import com.einschpanner.catchup.domain.post.dto.request.PostCreateRequest;
import com.einschpanner.catchup.domain.post.dto.response.PostListResponse;
import com.einschpanner.catchup.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public List<PostListResponse> findAllPosts(){
        return postService.findAll();
    }

//    @GetMapping("/{post_id}")
//    public Post findPost(){
//        return new Post();
//    }

    @PostMapping
    public String savePost(@RequestBody PostCreateRequest postCreateRequest){
        postService.save(postCreateRequest);
        return "savePost";
    }

    @PutMapping
    public String updatePost(){
        return "updatePost";
    }

    @DeleteMapping
    public String deletePost(){
        return "deletePost";
    }
}
