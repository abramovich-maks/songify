package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.GetSongDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.AddSongToAlbumResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.AddSongToArtistResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.AddSongToGenreResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.createAddSongToArtistResponse;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.createAddSongToGenreResponse;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.createPartiallyUpdatedSongResponse;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.createSongResponse;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromGetSongDtoToGetSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSongRequestDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToDeleteSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToGetAllSongsResponseDto;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongsController {

    private final SongifyCrudFasade songFasade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<SongDto> allSongs = songFasade.findAllSongs(pageable);
        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        GetSongDto song = songFasade.findSongDtoById(id);
        GetSongResponseDto response = mapFromGetSongDtoToGetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        SongDto savedSong = songFasade.addSong(request);
        CreateSongResponseDto createSongResponseDto = createSongResponse(savedSong);
        return ResponseEntity.ok(createSongResponseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Long id) {
        songFasade.deleteSongById(id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{songId}/artist/{artistId}")
    public ResponseEntity<AddSongToArtistResponseDto> addSongToArtist(@PathVariable Long songId, @PathVariable Long artistId) {
        songFasade.addSongToArtist(songId, artistId);
        AddSongToArtistResponseDto body = createAddSongToArtistResponse(songId, artistId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{songId}/album/{albumId}")
    public ResponseEntity<AddSongToAlbumResponseDto> addSongToAlbum(@PathVariable Long songId, @PathVariable Long albumId) {
        songFasade.addSongToAlbum(songId, albumId);
        AddSongToAlbumResponseDto body = new AddSongToAlbumResponseDto("Assigned song with id: " + songId + " to album with id: " + albumId, HttpStatus.OK);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{songId}/genre/{genreId}")
    public ResponseEntity<AddSongToGenreResponseDto> addSongToGenre(@PathVariable Long songId, @PathVariable Long genreId) {
        songFasade.addSongToGenre(songId, genreId);
        AddSongToGenreResponseDto body = createAddSongToGenreResponse(songId, genreId);
        return ResponseEntity.ok(body);
    }

    //    @PutMapping("/{id}")
//    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongRequestDto request) {
//        SongDto newSong = SongControllerMapper.fromUpdateRequest(request);
//        songFasade.updateById(id, newSong);
//        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
//        return ResponseEntity.ok(body);
//    }
//
    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id, @RequestBody PartiallyUpdateSongRequestDto request) {
        SongRequestDto requestDto = mapFromPartiallyUpdateSongRequestDtoToSongRequestDto(request);
        songFasade.updatePartiallyById(id, requestDto);
        PartiallyUpdateSongResponseDto body = createPartiallyUpdatedSongResponse(request);
        return ResponseEntity.ok(body);
    }
}
