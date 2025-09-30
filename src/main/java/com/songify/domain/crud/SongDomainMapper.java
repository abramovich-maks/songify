package com.songify.domain.crud;

import com.songify.infrastructure.crud.song.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;

class SongDomainMapper {

    public static SongEntity mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto updateSongRequestDto) {
        return new SongEntity(updateSongRequestDto.song(), updateSongRequestDto.artist());
    }

    public static SongEntity mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new SongEntity(dto.song(), dto.artist());
    }

    public static SongEntity mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new SongEntity(dto.song(), dto.artist());
    }

}
