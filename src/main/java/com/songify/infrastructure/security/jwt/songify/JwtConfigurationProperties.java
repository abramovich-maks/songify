package com.songify.infrastructure.security.jwt.songify;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "auth.jwt")
public record JwtConfigurationProperties(
        String secret,
        long expirationMinutes,
        String issuer
) {
}