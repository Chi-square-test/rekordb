package com.rekordb.rekordb.security.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekordb.rekordb.ApiStatus;
import com.rekordb.rekordb.ResponseDTO;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res);
        } catch (JwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ResponseDTO<Object> dto = ResponseDTO.builder().status(ApiStatus.EXPIRE).error(ex.getMessage()).build();

        String json = new ObjectMapper().writeValueAsString(dto);
        res.setStatus(status.value());
        res.getWriter().write(json);
    }
}
