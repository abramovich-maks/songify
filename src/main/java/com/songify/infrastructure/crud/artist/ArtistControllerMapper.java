package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.infrastructure.crud.album.AllAlbumsDtoResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

class ArtistControllerMapper {


    public static DeleteArtistResponseDto getDeleteArtistResponseDto(Long artistId) {
        return new DeleteArtistResponseDto("Artist with id: " + artistId + " have been deleted.", HttpStatus.OK);
    }

    public static AddArtistToAlbumResponseDto getAddArtistToAlbumResponseDto(Long artistId, Long albumId) {
        return new AddArtistToAlbumResponseDto("Assigned artist with id: " + artistId + " to album with id: " + albumId, HttpStatus.OK);
    }

    public static AllAlbumsDtoResponse mapFromAlbumDtoToAllAlbumsDtoResponse(final List<AlbumDto> allAlbums) {
        return new AllAlbumsDtoResponse(allAlbums);
    }
}
