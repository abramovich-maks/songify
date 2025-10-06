package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.GetSongDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
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

    public static GetSongResponseDto mapFromGetSongDtoToGetSongResponseDto(final GetSongDto song) {
        return new GetSongResponseDto(song.id(), song.name(), song.artists(), song.genre(), song.album(), song.releaseDate(), song.language());
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongRequestDto newSong) {
        return new UpdateSongResponseDto(newSong.name(), newSong.releaseDate(), newSong.duration());
    }

    public static SongControllerResponseDto mapFromSongToSongDto(SongDto song) {
        return SongControllerResponseDto.builder()
                .id(song.id())
                .name(song.name())
                .genre(song.genreName())
                .build();
    }

    public static AddSongToArtistResponseDto createAddSongToArtistResponse(final Long songId, final Long artistId) {
        return new AddSongToArtistResponseDto("Assigned song with id: " + songId + " to artist with id: " + artistId, HttpStatus.OK);
    }

    public static AddSongToGenreResponseDto createAddSongToGenreResponse(final Long songId, final Long genreId) {
        return new AddSongToGenreResponseDto("For the song with id: " + songId + " the genre was changed to id: " + genreId, HttpStatus.OK);
    }

    public static PartiallyUpdateSongResponseDto createPartiallyUpdatedSongResponse(final PartiallyUpdateSongRequestDto request) {
        return new PartiallyUpdateSongResponseDto(request.name(), request.releaseDate(), request.duration(), request.language(), request.genreId(), request.artistIds(), request.albumId());
    }

    public static SongRequestDto mapFromPartiallyUpdateSongRequestDtoToSongRequestDto(final PartiallyUpdateSongRequestDto request) {
        return new SongRequestDto(request.name(), request.releaseDate(), request.duration(), request.language(), request.genreId(), request.artistIds(), request.albumId());
    }
}
