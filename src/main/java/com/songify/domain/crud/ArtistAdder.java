package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDefaultDtoResponse;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDefaultRequestDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    ArtistDto addArtist(final String name) {
        Artist artist = new Artist(name);
        Artist save = artistRepository.save(artist);
        return new ArtistDto(save.getId(), save.getName());
    }

    ArtistDefaultDtoResponse addArtistWithDefaultAlbumAndSong(final ArtistRequestDto dto) {
        Artist artist = new Artist();
        artist.setName(dto.name());
        Artist savedArtist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("default-album:" + UUID.randomUUID());
        album.addArtist(savedArtist);
        Album savedAlbum = albumRepository.save(album);

        SongEntity song = new SongEntity();
        song.setName("default-song-name: " + UUID.randomUUID());
        song.addArtist(artist);
        song.addAlbum(album);
        SongEntity savedSong = songRepository.save(song);

        artist.addAlbum(album);

        return new ArtistDefaultDtoResponse(
                savedArtist.getId(),
                savedArtist.getName(),
                savedAlbum.getTitle(),
                savedSong.getName());
    }
}
