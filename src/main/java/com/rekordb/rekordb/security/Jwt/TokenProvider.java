package com.rekordb.rekordb.security.Jwt;

import com.rekordb.rekordb.security.Exception.TokenValidFailedException;
import com.rekordb.rekordb.user.domain.UserId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class TokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    private static final long ACCESS_EXPIRE_MINUTES = 30;
    private static final long REFRESH_EXPIRE_DAYS = 14;

    private static final long ADMIN_EXPIRE_DAYS = 30;

    public TokenProvider(String secret) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createRefreshToken(UserId userId) {
        Date expiryDate = Date.from(Instant.now().plus(REFRESH_EXPIRE_DAYS, ChronoUnit.DAYS));
        return new AuthToken(userId.getUserId(),expiryDate , key);
    }

    public AuthToken createAccessToken(UserId userId, String role) {
        Date expiryDate = Date.from(Instant.now().plus(ACCESS_EXPIRE_MINUTES, ChronoUnit.MINUTES));
        return new AuthToken(userId.getUserId(), role, expiryDate, key);
    }

    public AuthToken createDevToken(UserId userId, String role) {
        Date expiryDate = Date.from(Instant.now().plus(ADMIN_EXPIRE_DAYS, ChronoUnit.DAYS));
        return new AuthToken(userId.getUserId(), role, expiryDate, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        try {

            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } catch (Exception e ){
            throw new TokenValidFailedException();
        }
    }
}
