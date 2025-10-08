package com.songify.domain.crud.dto;

import java.time.Instant;

public record UpdateAlbumRequestDto(String title, Instant releaseDate) {}

