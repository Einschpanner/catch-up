package com.einschpanner.catchup.global.security.handler;

//import com.einschpanner.catchup.global.security.exception.UserNotFoundException;
import com.einschpanner.catchup.global.security.provider.JwtTokenProvider;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oauth2User.getAttributes().get("email");
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
//        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String token = jwtTokenProvider.createToken(user.getUserId(), Collections.singletonList("ROLE_USER"));

        response.addHeader("X-Auth-Token", "Bearer  " + token);
        logger.info("X-Auth-Token : " + response.getHeader("X-Auth-Token"));
    }


}