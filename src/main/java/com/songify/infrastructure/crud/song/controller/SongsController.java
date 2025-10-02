package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetSongResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.createSongResponse;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToDeleteSongResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToGetAllSongsResponseDto;
import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapFromSongToGetSongResponseDto;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongsController {

    private final SongifyCrudFasade songFasade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault(page = 0,size = 10) Pageable pageable) {
        List<SongDto> allSongs = songFasade.findAllSongs(pageable);
        GetAllSongsResponseDto response = mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto songEntity = songFasade.findSongDtoById(id);
        GetSongResponseDto response = mapFromSongToGetSongResponseDto(songEntity);
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

//    @PutMapping("/{id}")
//    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongRequestDto request) {
//        SongDto newSong = SongControllerMapper.fromUpdateRequest(request);
//        songFasade.updateById(id, newSong);
//        UpdateSongResponseDto body = mapFromSongToUpdateSongResponseDto(newSong);
//        return ResponseEntity.ok(body);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id, @RequestBody PartiallyUpdateSongRequestDto request) {
//        SongDto updatedSong = SongControllerMapper.fromPartialRequest(request);
//        SongDto saveSong = songFasade.updatePartiallyById(id, updatedSong);
//        PartiallyUpdateSongResponseDto body = mapFromSongToPartiallyUpdateSongResponseDto(saveSong);
//        return ResponseEntity.ok(body);
//    }
}
