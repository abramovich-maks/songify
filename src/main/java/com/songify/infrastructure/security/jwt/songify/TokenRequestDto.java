package com.songify.infrastructure.security.jwt.songify;

public record TokenRequestDto(String username, String password) {
}
