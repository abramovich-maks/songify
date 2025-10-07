package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.ArtistWithAlbumDto;
import com.songify.infrastructure.crud.album.AllAlbumsDtoResponse;
import jakarta.validation.Valid;
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
import java.util.Set;

import static com.songify.infrastructure.crud.artist.ArtistControllerMapper.getAddArtistToAlbumResponseDto;
import static com.songify.infrastructure.crud.artist.ArtistControllerMapper.getDeleteArtistResponseDto;
import static com.songify.infrastructure.crud.artist.ArtistControllerMapper.mapFromAlbumDtoToAllAlbumsDtoResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
class ArtistController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping()
    public ResponseEntity<ArtistDto> postArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFasade.addArtist(artistRequestDto);
        return ResponseEntity.ok(artistDto);
    }

    @PostMapping("/album/song")
    public ResponseEntity<ArtistDto> addArtistWithDefaultAlbumAndSing(@RequestBody ArtistRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFasade.addArtistWithDefaultAlbumAndSing(artistRequestDto);
        return ResponseEntity.ok(artistDto);
    }

    @GetMapping
    public ResponseEntity<AllArtistsDto> getArtist(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(pageable);
        AllArtistsDto allArtistsDto = new AllArtistsDto(allArtists);
        return ResponseEntity.ok(allArtistsDto);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistWithAlbumDto> findArtistByIdWithAlbum(@PathVariable Long artistId) {
        ArtistWithAlbumDto artistByIdWithAlbum = songifyCrudFasade.findArtistByIdWithAlbum(artistId);
        return ResponseEntity.ok(artistByIdWithAlbum);
    }

    @GetMapping("/{artistId}/albums")
    public ResponseEntity<AllAlbumsDtoResponse> getAllAlbumsByArtistId(@PathVariable Long artistId) {
        List<AlbumDto> allAlbums = songifyCrudFasade.getAlbumsByArtistId(artistId);
        AllAlbumsDtoResponse albumResponse = mapFromAlbumDtoToAllAlbumsDtoResponse(allAlbums);
        return ResponseEntity.ok(albumResponse);
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<DeleteArtistResponseDto> deleteArtistWithAlbumsAndSongs(@PathVariable Long artistId) {
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        DeleteArtistResponseDto body = getDeleteArtistResponseDto(artistId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{artistId}/{albumId}")
    public ResponseEntity<AddArtistToAlbumResponseDto> addArtistToAlbum(@PathVariable Long artistId, @PathVariable Long albumId) {
        songifyCrudFasade.addArtistToAlbum(artistId, albumId);
        AddArtistToAlbumResponseDto body = getAddArtistToAlbumResponseDto(artistId, albumId);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{artistId}")
    public ResponseEntity<ArtistDto> updateArtistNameById(@PathVariable Long artistId, @RequestBody @Valid ArtistUpdateRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFasade.updateArtistNameById(artistId, artistRequestDto.newArtistName());
        return ResponseEntity.ok(artistDto);
    }
}
