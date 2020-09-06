package com.einschpanner.catchup.global.security.handler;

import com.einschpanner.catchup.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        String id = authentication.getName();

        String token = jwtTokenProvider.createToken(id, Collections.singletonList("ROLE_USER"));

        response.addHeader("X-Auth-Token", "Bearer  " + token);
        logger.info("X-Auth-Token : " + response.getHeader("X-Auth-Token"));

        redirectStrategy.sendRedirect(request, response, "/auth");
    }


}