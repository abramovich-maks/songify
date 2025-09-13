package com.songify.song.infrastructure.controller;

import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.SongEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class SongMapper {

    public static SongEntity mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new SongEntity(dto.song(), dto.artist());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(SongEntity song) {
        return new CreateSongResponseDto(song);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(Map<Integer, SongEntity> database) {
        return new GetAllSongsResponseDto(database);
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(SongEntity song) {
        return new GetSongResponseDto(song);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Integer id) {
        return new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK);
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(SongEntity updatedSong) {
        return new PartiallyUpdateSongResponseDto(updatedSong);
    }

    public static SongEntity mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new SongEntity(dto.song(), dto.artist());
    }
    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongEntity newSong) {
        return new UpdateSongResponseDto(newSong.song(), newSong.artist());
    }

    public static SongEntity mapFromUpdateSongRequestDtoToSong(String newSongName, String newArtist) {
        return new SongEntity(newSongName, newArtist);
    }
}

