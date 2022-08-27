package com.rekordb.rekordb.security.Jwt;

import com.rekordb.rekordb.user.domain.userInfo.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Column(name = "userId", unique = true)
    @NotNull
    @EmbeddedId
    private UserId userId;

    @Column(name = "REFRESH_TOKEN", length = 256)
    @NotNull
    @Size(max = 256)
    private String refreshToken;

    @Column(name = "ACCESS_TOKEN", length = 256)
    @NotNull
    @Size(max = 256)
    private String accessToken;

}
