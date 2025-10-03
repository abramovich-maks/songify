package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistAndSongs;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<AlbumDtoWithArtistAndSongs> getAlbums(@PathVariable final Long albumId) {
        AlbumDtoWithArtistAndSongs albumByIdWithArtistAndSong = songifyCrudFasade.findAlbumByIdWithArtistAndSong(albumId);
        return ResponseEntity.ok(albumByIdWithArtistAndSong);
    }

    @GetMapping()
    public ResponseEntity<AllAlbumsDtoResponse> getAlbums(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        List<AlbumDto> allAlbums = songifyCrudFasade.findAllAlbums(pageable);
        AllAlbumsDtoResponse albumResponse = mapFromAlbumDtoToAllAlbumsDtoResponse(allAlbums);
        return ResponseEntity.ok(albumResponse);
    }
}
