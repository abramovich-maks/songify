package com.songify.infrastructure.crud.artist;

import org.springframework.http.HttpStatus;

class ArtistControllerMapper {


    public static DeleteArtistResponseDto getDeleteArtistResponseDto(Long artistId) {
        return new DeleteArtistResponseDto("Artist with id: " + artistId + " have been deleted.", HttpStatus.OK);
    }
}
