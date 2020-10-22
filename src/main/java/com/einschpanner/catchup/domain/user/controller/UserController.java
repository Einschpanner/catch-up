package com.einschpanner.catchup.domain.user.controller;

import com.einschpanner.catchup.domain.user.dto.ProfileDto;
import com.einschpanner.catchup.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileDto.UpdateRes saveProfile(
            @RequestBody final ProfileDto.UpdateReq updateReq
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        return userService.saveProfile(userId, updateReq);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDto.Res getProfile(
            @PathVariable final long userId
    ) {
        return userService.getProfile(userId);
    }

}
