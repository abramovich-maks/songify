package com.songify.infrastructure.crud.album;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
class PartiallyUpdateAlbumResponseDto {

    private String title;
    private Instant releaseDate;

}
