package com.songify.infrastructure.crud.artist;

import org.springframework.http.HttpStatus;

class ArtistControllerMapper {


    public static DeleteArtistResponseDto getDeleteArtistResponseDto(Long artistId) {
        return new DeleteArtistResponseDto("Artist with id: " + artistId + " have been deleted.", HttpStatus.OK);
    }

    public static AddArtistToAlbumResponseDto getAddArtistToAlbumResponseDto(Long artistId, Long albumId) {
        return new AddArtistToAlbumResponseDto("Assigned artist with id: " + artistId + " to album with id: " + albumId, HttpStatus.OK);
    }
}
