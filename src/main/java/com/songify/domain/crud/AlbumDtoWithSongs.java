package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;

import java.util.Set;

public record AlbumDtoWithSongs(Set<SongDto> songs) {
}
