package com.songify.infrastructure.security.jwt.songify;

import lombok.Builder;

@Builder
public record JwtResponseDto(String token) {
}