package com.songify.domain.crud.dto;

import java.util.List;

public record GenreDtoWithSongsAndArtist(
        GenreDto genre,
        List<SongWithArtistDto> songs
) {
}
