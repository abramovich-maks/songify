package com.songify.domain.crud;

import java.time.Instant;

public record UpdateAlbumRequestDto(String title, Instant releaseDate) {}

