package com.rekordb.rekordb.security.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import com.rekordb.rekordb.utils.HeaderUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenStr = HeaderUtils.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);


       if (tokenStr!=null) {
           try{
               String userid = token.getTokenClaims().getSubject();
               log.info("토큰 접속 : "+userid);
           }
           catch (SecurityException e) {
               log.info("Invalid JWT signature.");
               throw new JwtException("토큰이 유효하지 않음");
           } catch (MalformedJwtException e) {
               log.info("Invalid JWT token.");
               throw new JwtException("토큰이 유효하지 않음");
           } catch (ExpiredJwtException e) {
               log.info("Expired JWT token.");
               throw new JwtException("토큰이 만료되어짐");
           } catch (UnsupportedJwtException e) {
               log.info("Unsupported JWT token.");
               throw new JwtException("토큰이 유효하지 않음");
           } catch (IllegalArgumentException e) {
               log.info("JWT token compact of handler are invalid.");
           }
           Authentication authentication = tokenProvider.getAuthentication(token);
           SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,  response);
    }
}
