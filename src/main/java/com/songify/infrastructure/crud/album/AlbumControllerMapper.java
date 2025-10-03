package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.dto.AlbumDto;

import java.util.List;

class AlbumControllerMapper {

    public static AllAlbumsDtoResponse mapFromAlbumDtoToAllAlbumsDtoResponse(final List<AlbumDto> allAlbums) {
        return new AllAlbumsDtoResponse(allAlbums);
    }
}
