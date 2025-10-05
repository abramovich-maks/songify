package com.songify.domain.crud;

import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;

class SongDomainMapper {

    public static SongEntity mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto updateSongRequestDto) {
        return new SongEntity(updateSongRequestDto.song());
    }

}
