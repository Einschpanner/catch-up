package com.einschpanner.catchup.domain.post.controller;

import com.einschpanner.catchup.domain.post.domain.PostLike;
import com.einschpanner.catchup.domain.post.service.PostLikeService;
import com.einschpanner.catchup.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void togglePostLike(
            @PathVariable final Long postId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        postLikeService.toggle(postId, userId);
    }

    @GetMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto.Res> findAllPostLikes(
            @PathVariable final Long postId
    ) {
        List<PostLike> postLikes = postLikeService.findAllByPostId(postId);
        return postLikes.stream()
                .map(PostLike::getUser)
                .map(UserDto.Res::new)
                .collect(Collectors.toList());
    }
}
