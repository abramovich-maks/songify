package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.UpdateAlbumRequestDto;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.infrastructure.crud.song.controller.dto.response.ActionResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

class AlbumControllerMapper {

    public static AllAlbumsDtoResponse mapFromAlbumDtoToAllAlbumsDtoResponse(final List<AlbumDto> allAlbums) {
        return new AllAlbumsDtoResponse(allAlbums);
    }

    public static ActionResponseDto getDeleteAlbumResponseDto(final Long albumId) {
        return new ActionResponseDto("Album with id: " + albumId + " deleted successfully", HttpStatus.OK);
    }

    public static ActionResponseDto getAddSongToAlbumResponseDto(final Long albumId, final Long songId) {
        return new ActionResponseDto("Assigned song with id: " + songId + " to album with id: " + albumId, HttpStatus.OK);
    }

    public static ActionResponseDto getDeleteSongFromAlbumResponseDto(final Long albumId, final Long songId) {
        return new ActionResponseDto("Song with id: " + songId + " has been deleted from album with id: " + albumId, HttpStatus.OK);
    }

    public static ActionResponseDto getAddArtistToAlbumResponseDto(final Long albumId, final Long artistId) {
        return new ActionResponseDto("Assigned artist with id: " + artistId + " to album with id: " + albumId, HttpStatus.OK);
    }

    public static ActionResponseDto getDeleteArtistFromAlbumDto(final Long albumId, final Long artistId) {
        return new ActionResponseDto("Artist with id: " + artistId + " has been deleted from album with id: " + albumId, HttpStatus.OK);
    }

    public static PartiallyUpdateAlbumResponseDto createPartiallyUpdatedAlbumResponse(final UpdateAlbumRequestDto request) {
        return new PartiallyUpdateAlbumResponseDto(request.title(), request.releaseDate());
    }
}
