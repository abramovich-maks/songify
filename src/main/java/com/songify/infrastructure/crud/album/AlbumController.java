package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.UpdateAlbumRequestDto;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistAndSongs;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.ActionResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.songify.infrastructure.crud.album.AlbumControllerMapper.createPartiallyUpdatedAlbumResponse;
import static com.songify.infrastructure.crud.album.AlbumControllerMapper.getAddArtistToAlbumResponseDto;
import static com.songify.infrastructure.crud.album.AlbumControllerMapper.getAddSongToAlbumResponseDto;
import static com.songify.infrastructure.crud.album.AlbumControllerMapper.getDeleteAlbumResponseDto;
import static com.songify.infrastructure.crud.album.AlbumControllerMapper.getDeleteArtistFromAlbumDto;
import static com.songify.infrastructure.crud.album.AlbumControllerMapper.getDeleteSongFromAlbumResponseDto;
import static com.songify.infrastructure.crud.album.AlbumControllerMapper.mapFromAlbumDtoToAllAlbumsDtoResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/albums")
class AlbumController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping()
    public ResponseEntity<AlbumDto> postAlbum(@RequestBody AlbumRequestDto albumRequestDto) {
        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(albumRequestDto);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDtoWithArtistAndSongs> getAlbumById(@PathVariable final Long albumId) {
        AlbumDtoWithArtistAndSongs albumByIdWithArtistAndSong = songifyCrudFasade.findAlbumByIdWithArtistAndSong(albumId);
        return ResponseEntity.ok(albumByIdWithArtistAndSong);
    }

    @GetMapping()
    public ResponseEntity<AllAlbumsDtoResponse> getAlbums(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<AlbumDto> allAlbums = songifyCrudFasade.findAllAlbums(pageable);
        AllAlbumsDtoResponse albumResponse = mapFromAlbumDtoToAllAlbumsDtoResponse(allAlbums);
        return ResponseEntity.ok(albumResponse);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<ActionResponseDto> deleteAlbum(@PathVariable Long albumId) {
        songifyCrudFasade.deleteAlbumById(albumId);
        ActionResponseDto body = getDeleteAlbumResponseDto(albumId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{albumId}/song/{songId}")
    public ResponseEntity<ActionResponseDto> addSong(@PathVariable Long albumId, @PathVariable Long songId) {
        songifyCrudFasade.addSongToAlbumById(albumId, songId);
        ActionResponseDto body = getAddSongToAlbumResponseDto(albumId, songId);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{albumId}/song/{songId}")
    public ResponseEntity<ActionResponseDto> deleteSongFromAlbum(@PathVariable Long albumId, @PathVariable Long songId) {
        songifyCrudFasade.deleteSongFromAlbumById(albumId, songId);
        ActionResponseDto body = getDeleteSongFromAlbumResponseDto(albumId, songId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{albumId}/artist/{artistId}")
    public ResponseEntity<ActionResponseDto> addArtistToAlbumById(@PathVariable Long albumId, @PathVariable Long artistId) {
        songifyCrudFasade.addArtistToAlbumById(albumId, artistId);
        ActionResponseDto body = getAddArtistToAlbumResponseDto(albumId, artistId);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{albumId}/artist/{artistId}")
    public ResponseEntity<ActionResponseDto> deleteArtistFromAlbum(@PathVariable Long albumId, @PathVariable Long artistId) {
        songifyCrudFasade.deleteArtistFromAlbumById(albumId, artistId);
        ActionResponseDto body = getDeleteArtistFromAlbumDto(albumId, artistId);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{albumId}")
    public ResponseEntity<PartiallyUpdateAlbumResponseDto> updateAlbum(@PathVariable Long albumId, @RequestBody UpdateAlbumRequestDto request) {
        songifyCrudFasade.updateAlbumNameOrReleaseById(albumId, request);
        PartiallyUpdateAlbumResponseDto body = createPartiallyUpdatedAlbumResponse(request);
        return ResponseEntity.ok(body);
    }
}
