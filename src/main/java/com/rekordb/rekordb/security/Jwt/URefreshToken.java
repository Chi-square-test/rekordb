package com.rekordb.rekordb.security.Jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rekordb.rekordb.user.UserOauthId;
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
public class URefreshToken {
    @JsonIgnore
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "OauthId", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private UserOauthId oauthId;

    @Column(name = "REFRESH_TOKEN", length = 256)
    @NotNull
    @Size(max = 256)
    private String token;

    public URefreshToken(@NotNull @Size(max = 64) UserOauthId oauthId, @NotNull @Size(max = 256) String token) {
        this.oauthId = oauthId;
        this.token = token;
    }
}
