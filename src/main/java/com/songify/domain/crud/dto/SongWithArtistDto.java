package com.songify.domain.crud.dto;

import java.util.List;

public record SongWithArtistDto(
        Long id,
        String songName,
        List<ArtistDto> artist
) {
}
