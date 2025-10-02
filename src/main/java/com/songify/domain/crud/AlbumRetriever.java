package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistAndSongs;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    AlbumDtoWithArtistAndSongs findAlbumByIdWithArtistAndSongs(final Long id) {
        Album album = albumRepository.findAlbumByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new AlbumNotFoundExceptions("Album with id: " + id + " not found"));

        AlbumDto albumDto = new AlbumDto(album.getId(), album.getTitle());

        Set<ArtistDto> artistDto = album.getArtists().stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                .collect(Collectors.toSet());

        Set<SongDto> songDto = album.getSongs().stream()
                .map(song -> new SongDto(song.getId(), song.getName()))
                .collect(Collectors.toSet());

        return new AlbumDtoWithArtistAndSongs(albumDto, artistDto, songDto);
    }

    Set<Album> findAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId);
    }
}