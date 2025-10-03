package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;
    private final AlbumAdder albumAdder;
    private final SongAdder songAdder;

    ArtistDto addArtist(final String name) {
        Artist artist = new Artist(name);
        Artist save = artistRepository.save(artist);
        return new ArtistDto(save.getId(), save.getName());
    }

    ArtistDto addArtistWithDefaultAlbumAndSong(final ArtistRequestDto dto) {
        String artistName = dto.name();
        Artist save = createArtistWithDefaultAlbumAndSong(artistName);
        return new ArtistDto(save.getId(), save.getName());
    }

    private Artist createArtistWithDefaultAlbumAndSong(final String artistName) {
        Album album = createDefaultAlbum();
        SongEntity song = createDefaultSong();

        Artist artist = new Artist(artistName);
        album.addSong(song);
        artist.addAlbum(album);
        return artistRepository.save(artist);
    }

    private Album createDefaultAlbum() {
        return albumAdder.addAlbum(
                "default-album: " + UUID.randomUUID(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    private SongEntity createDefaultSong() {
        return songAdder.addDefaultSong(new SongRequestDto(
                "default-song-name: " + UUID.randomUUID(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                0L,
                SongLanguageDto.DEFAULT
        ));
    }
}
