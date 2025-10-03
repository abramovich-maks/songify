package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.dto.AlbumDto;

import java.util.List;

public record AllAlbumsDtoResponse(List<AlbumDto> allAlbums) {
}
