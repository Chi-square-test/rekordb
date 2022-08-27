package com.rekordb.rekordb.security;

import com.rekordb.rekordb.security.Exception.TokenAccessDeniedHandler;
import com.rekordb.rekordb.security.Jwt.TokenAuthFilter;
import com.rekordb.rekordb.security.Jwt.TokenExceptionFilter;
import com.rekordb.rekordb.security.Jwt.TokenProvider;
import com.rekordb.rekordb.user.domain.userInfo.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/onlymanager/**","/tourspotadmin/**","/tagadmin/**").hasAnyAuthority(RoleType.ADMIN.getName())
                    .antMatchers("/authapi/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        new TokenAuthFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(new TokenExceptionFilter(), TokenAuthFilter.class);

        return http.build();
    }
}

