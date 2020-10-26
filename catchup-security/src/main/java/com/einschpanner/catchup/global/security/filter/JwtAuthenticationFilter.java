package com.einschpanner.catchup.global.security.filter;


import com.einschpanner.catchup.global.security.provider.JwtTokenProvider;
import com.einschpanner.catchup.global.security.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.util.DecodingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    ObjectMapper objectMapper;

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        Claims claims = null;
//        try {
//            claims = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//            if (claims != null)
//                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(claims));
//            filterChain.doFilter(request, response);
//        } catch (SignatureException e) {
//            sendErrorMessage((HttpServletResponse) response, "유효하지 않은 토큰입니다.");
//        } catch (MalformedJwtException e) {
//            sendErrorMessage((HttpServletResponse) response, "손상된 토큰입니다.");
//        } catch (DecodingException e) {
//            sendErrorMessage((HttpServletResponse) response, "잘못된 인증입니다.");
//        } catch (ExpiredJwtException e) {
//            sendErrorMessage((HttpServletResponse) response, "만료된 토큰입니다.");
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Claims claims = null;
        try {
            claims = jwtTokenProvider.resolveToken(request);
            if (claims != null)
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(claims));
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            sendErrorMessage(response, "유효하지 않은 토큰입니다.");
        } catch (MalformedJwtException e) {
            sendErrorMessage(response, "손상된 토큰입니다.");
        } catch (DecodingException e) {
            sendErrorMessage(response, "잘못된 인증입니다.");
        } catch (ExpiredJwtException e) {
            sendErrorMessage(response, "만료된 토큰입니다.");
        }
    }

    private void sendErrorMessage(HttpServletResponse res, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.getWriter().write(this.objectMapper.writeValueAsString(new ErrorResponse(message)));

    }
}