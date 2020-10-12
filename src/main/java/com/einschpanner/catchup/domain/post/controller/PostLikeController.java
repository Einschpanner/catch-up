package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.post.dto.PostLikeDto;
import com.einschpanner.catchup.domain.post.service.PostLikeService;
import com.einschpanner.catchup.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void togglePostLike(
            @PathVariable final Long postId,
            @RequestBody PostLikeDto.Req postDto
    ){

        postLikeService.toggle(postId, postDto.getUserId());
    }

    @GetMapping("/{postId}/likes")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto.Res> findAllPostLikes(
            @PathVariable final Long postId
    ){
        List<PostLike> postLikes = postLikeService.findAllByPostId(postId);
        return postLikes.stream()
                .map(PostLike::getUser)
                .map(UserDto.Res::new)
                .collect(Collectors.toList());
    }
}
