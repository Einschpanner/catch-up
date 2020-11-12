package com.einschpanner.catchup.follow.api;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.follow.dto.FollowDto;
import com.einschpanner.catchup.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("following")
    @ResponseStatus(HttpStatus.OK)
    public List<FollowDto.BlogRes> findMyFollowingBlog(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        List<Follow> follows = followService.findAllFollowing(userId);
        return follows.stream()
                .map(FollowDto.BlogRes::new)
                .collect(Collectors.toList());
    }

    @PostMapping("following/{subscribeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleFollow(
            @PathVariable final Long subscribeId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        followService.toggle(userId, subscribeId);
    }

    @GetMapping("following/{subscribeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FollowDto.FollowingRes> findFollowing(
            @PathVariable final Long subscribeId
    ){
        List<Follow> followings = followService.findAllFollowing(subscribeId);
        return followings.stream()
                .map(FollowDto.FollowingRes::new)
                .collect(Collectors.toList());
    }

    @GetMapping("follower/{subscribedId}")
    @ResponseStatus(HttpStatus.OK)
    public List<FollowDto.FollowerRes> findFollower(
            @PathVariable final Long subscribedId
    ){
        List<Follow> followers = followService.findAllFollower(subscribedId);
        return followers.stream()
                .map(FollowDto.FollowerRes::new)
                .collect(Collectors.toList());
    }
}