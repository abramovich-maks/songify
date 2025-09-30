package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongControllerResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class SongControllerMapper {

    public static SongDto fromCreateRequest(CreateSongRequestDto req) {
        return new SongDto(null, req.song(), req.artist());
    }

    public static SongDto fromUpdateRequest(UpdateSongRequestDto req) {
        return new SongDto(null, req.song(), req.artist());
    }

    public static SongDto fromPartialRequest(PartiallyUpdateSongRequestDto req) {
        return new SongDto(null, req.song(), req.artist());
    }

    public static CreateSongResponseDto createSongResponse(SongDto song) {
        SongControllerResponseDto dto = mapFromSongToSongDto(song);
        return new CreateSongResponseDto(dto);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<SongDto> songs) {
        List<SongControllerResponseDto> dtos = songs.stream()
                .map(SongControllerMapper::mapFromSongToSongDto)
                .collect(Collectors.toList());
        return new GetAllSongsResponseDto(dtos);
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(SongDto song) {
        SongControllerResponseDto dto = mapFromSongToSongDto(song);
        return new GetSongResponseDto(dto);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK);
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(SongDto updatedSong) {
        SongControllerResponseDto dto = mapFromSongToSongDto(updatedSong);
        return new PartiallyUpdateSongResponseDto(dto);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongDto newSong) {
        return new UpdateSongResponseDto(newSong.id(), newSong.name(), newSong.artist());
    }

    public static SongControllerResponseDto mapFromSongToSongDto(SongDto song) {
        if (song == null) return null;
        return SongControllerResponseDto.builder()
                .name(song.name())
                .artist(song.artist())
                .build();
    }
}
