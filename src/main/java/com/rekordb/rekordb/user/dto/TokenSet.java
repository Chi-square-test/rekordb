package com.rekordb.rekordb.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSet {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String NickName;
    private String AccessToken;
    private String RefreshToken;

    public TokenSet(String accessToken,String refreshToken){
        this.AccessToken = accessToken;
        this.RefreshToken = refreshToken;
    }

    public static TokenSet addNickNameFactory(TokenSet tokenSet, String name){
        return TokenSet.builder()
                .AccessToken(tokenSet.getAccessToken())
                .RefreshToken(tokenSet.getRefreshToken())
                .NickName(name)
                .build();
    }
}
