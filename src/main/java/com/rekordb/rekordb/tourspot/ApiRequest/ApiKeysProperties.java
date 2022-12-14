package com.rekordb.rekordb.tourspot.ApiRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "api-key")
public class ApiKeysProperties {
    private final String googleKey;
    private final String tourKey;
    private final String jusoKey;
}
