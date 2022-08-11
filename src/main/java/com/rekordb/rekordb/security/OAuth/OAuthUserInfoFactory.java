package com.rekordb.rekordb.security.OAuth;

import com.rekordb.rekordb.user.ProviderType;

import java.util.Map;

public class OAuthUserInfoFactory {
    public static OAuthUserInfo getOAuthUserInfo(ProviderType providerType, Map<String, Object> attributes) {
        return switch (providerType) {
            case GOOGLE -> new GoogleOAuthUserInfo(attributes);
            case NAVER -> new NaverOAuthUserInfo(attributes);
            case KAKAO-> new KakaoOAuthUserInfo(attributes);
        };
    }
}
