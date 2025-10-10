package com.songify.infrastructure.crud.artist;

public record ArtistDefaultDto(
        Long artistId,
        String artistName,
        String albumName,
        String songName
) {
}
