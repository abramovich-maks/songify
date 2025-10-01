package com.songify.domain.crud.dto;

import java.util.Set;

public record AlbumDtoWithArtistAndSongs(
        AlbumDto album,
        Set<ArtistDto> artist,
        Set<SongDto> song
) {
}