package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.*;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.domain.model.SongEntity;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.songify.song.infrastructure.controller.SongMapper.*;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongsController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public SongsController(SongAdder songAdder, SongRetriever songRetriever, SongDeleter songDeleter, SongUpdater songUpdater) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songDeleter = songDeleter;
        this.songUpdater = songUpdater;
    }

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(Pageable pageable) {
        List<SongEntity> allSongs = songRetriever.findAll(pageable);
        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongEntity songEntity = songRetriever.findById(id);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(songEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity song = mapFromCreateSongRequestDtoToSong(request);
        SongEntity savedSong = songAdder.addSong(song);
        CreateSongResponseDto createSongResponseDto = mapFromSongToCreateSongResponseDto(savedSong);
        return ResponseEntity.ok(createSongResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Long id) {
        songDeleter.deleteById(id);
        DeleteSongResponseDto body = mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongRequestDto request) {
        SongEntity newSong = mapFromUpdateSongRequestDtoToSong(request);
        songUpdater.updateById(id, newSong);
        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id, @RequestBody PartiallyUpdateSongRequestDto request) {
        SongEntity updatedSong = mapFromPartiallyUpdateSongRequestDtoToSong(request);
        SongEntity saveSong = songUpdater.updatePartiallyById(id, updatedSong);
        PartiallyUpdateSongResponseDto body = mapFromSongToPartiallyUpdateSongResponseDto(saveSong);
        return ResponseEntity.ok(body);
    }
}
