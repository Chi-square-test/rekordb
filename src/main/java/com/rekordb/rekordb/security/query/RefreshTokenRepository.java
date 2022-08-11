package com.rekordb.rekordb.security.query;

import com.rekordb.rekordb.security.Jwt.URefreshToken;
import com.rekordb.rekordb.user.UserOauthId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<URefreshToken,Long> {
    URefreshToken findByOauthId(UserOauthId id);
    URefreshToken findByOauthIdAndToken(UserOauthId id, String refreshToken);

}
