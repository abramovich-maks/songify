package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.dto.AlbumDto;
import org.springframework.http.HttpStatus;

import java.util.List;

class AlbumControllerMapper {

    public static AllAlbumsDtoResponse mapFromAlbumDtoToAllAlbumsDtoResponse(final List<AlbumDto> allAlbums) {
        return new AllAlbumsDtoResponse(allAlbums);
    }

    public static DeleteAlbumResponseDto getDeleteAlbumResponseDto(final Long albumId) {
        return new DeleteAlbumResponseDto("Album with id: " + albumId + " deleted successfully", HttpStatus.OK);
    }
}
