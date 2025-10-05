package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.AddSongToArtistResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.AddSongToGenreResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongControllerResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class SongControllerMapper {

    public static SongDto fromCreateRequest(CreateSongRequestDto req) {
        return new SongDto(null, req.song());
    }

    public static SongDto fromUpdateRequest(UpdateSongRequestDto req) {
        return new SongDto(null, req.song());
    }

    public static SongDto fromPartialRequest(PartiallyUpdateSongRequestDto req) {
        return new SongDto(null, req.song());
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

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongRequestDto newSong) {
        return new UpdateSongResponseDto(newSong.name(), newSong.releaseDate(), newSong.duration());
    }

    public static SongControllerResponseDto mapFromSongToSongDto(SongDto song) {
        return SongControllerResponseDto.builder()
                .id(song.id())
                .name(song.name())
                .build();
    }

    public static AddSongToArtistResponseDto createAddSongToArtistResponse(final Long songId, final Long artistId) {
        return new AddSongToArtistResponseDto("Assigned song with id: " + songId + " to artist with id: " + artistId, HttpStatus.OK);
    }

    public static AddSongToGenreResponseDto createAddSongToGenreResponse(final Long songId, final Long genreId) {
        return new AddSongToGenreResponseDto("For the song with id: " + songId + " the genre was changed to id: " + genreId, HttpStatus.OK);
    }
}
