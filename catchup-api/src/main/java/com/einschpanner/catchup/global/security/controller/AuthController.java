package com.einschpanner.catchup.global.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String checkAuth(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long)authentication.getPrincipal();
        return "userId  = " + userId;
    }
}
