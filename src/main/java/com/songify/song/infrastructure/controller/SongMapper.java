package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {

    public static SongEntity mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new SongEntity(dto.song(), dto.artist());
    }

    public static SongDto mapFromSongToSongDto(SongEntity song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongEntity song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new CreateSongResponseDto(songDto);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<SongEntity> songs) {
        List<SongDto> songDtos = songs.stream()
                .map(SongMapper::mapFromSongToSongDto)
                .toList();
        return new GetAllSongsResponseDto(songDtos);
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(SongEntity song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK);
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(SongEntity updatedSong) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(updatedSong);
        return new PartiallyUpdateSongResponseDto(songDto);
    }

    public static SongEntity mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new SongEntity(dto.song(), dto.artist());
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongEntity newSong) {
        return new UpdateSongResponseDto(newSong.getName(), newSong.getArtist());
    }

    public static SongEntity mapFromUpdateSongRequestDtoToSong(UpdateSongRequestDto updateSongRequestDto) {
        return new SongEntity(updateSongRequestDto.song(), updateSongRequestDto.artist());
    }
}

