package com.einschpanner.catchup.global.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");
        Authentication auth =
                new UsernamePasswordAuthenticationToken(customUser.id(), "", roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        context.setAuthentication(auth);
        return context;
    }
}