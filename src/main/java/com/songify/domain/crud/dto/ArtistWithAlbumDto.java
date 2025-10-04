package com.songify.domain.crud.dto;

import java.util.List;

public record ArtistWithAlbumDto(ArtistDto artist, List<AlbumDto> albums) {
}
