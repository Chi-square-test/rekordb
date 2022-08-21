package com.rekordb.rekordb.security.query;

import com.rekordb.rekordb.security.Jwt.RefreshToken;
import com.rekordb.rekordb.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,UserId> {

    Optional<RefreshToken> findByRefreshToken(String refresh);
    Optional<RefreshToken> findByAccessToken(String refresh);
    Optional<RefreshToken> findByUserIdAndRefreshTokenAndAccessToken(UserId userId,String refresh, String access);
}
