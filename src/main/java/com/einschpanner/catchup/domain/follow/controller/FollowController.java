package com.einschpanner.catchup.domain.follow.controller;


import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.follow.dto.FollowDto;
import com.einschpanner.catchup.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 내가 구독한 사용자의 블로그 포스팅 불러오기
    @GetMapping("following")
    @ResponseStatus(HttpStatus.OK)
    public void findMyFollowingBlog(){
//        TODO : userId 받는 로직 추가, Blog 기능 추가 이후 로직 추가
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long userId = (Long) authentication.getPrincipal();
        Long userId = 1L;

    }

    @PostMapping("following/{subscribeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleFollow(
            @PathVariable final Long subscribeId
    ){
//        TODO : userId 받는 로직 추가
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Long userId = (Long) authentication.getPrincipal();
        Long userId = 1L;
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
