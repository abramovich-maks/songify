package com.songify.infrastructure.crud.song.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.songify.domain.crud.dto.SongLanguageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
public class PartiallyUpdateSongResponseDto {

    private String name;
    private Instant releaseDate;
    private Long duration;
    private SongLanguageDto language;
    private Long genreId;
    private Set<Long> artistIds;
    private Long albumId;
}