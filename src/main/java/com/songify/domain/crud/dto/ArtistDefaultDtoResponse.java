package com.songify.domain.crud.dto;

public record ArtistDefaultDtoResponse(
        Long artistId,
        String artistName,
        String albumName,
        String songName
) {
}
