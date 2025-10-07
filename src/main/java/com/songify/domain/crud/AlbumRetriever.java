package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistAndSongs;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;

    AlbumDtoWithArtistAndSongs findAlbumByIdWithArtistAndSongs(final Long id) {
        Album album = albumRepository.findAlbumByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + id + " not found"));

        AlbumDto albumDto = new AlbumDto(album.getId(), album.getTitle());

        Set<ArtistDto> artistDto = album.getArtists().stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                .collect(Collectors.toSet());

        Set<SongDto> songDto = album.getSongs().stream()
                .map(song -> {
                    String genreName = song.getGenre() != null ? song.getGenre().getName() : "default";
                    return new SongDto(song.getId(), song.getName(), genreName);
                })
                .collect(Collectors.toSet());

        return new AlbumDtoWithArtistAndSongs(albumDto, artistDto, songDto);
    }

    Set<Album> findAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId);
    }

    Album findById(final Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + albumId + " not found"));
    }

    List<AlbumDto> findAll(final Pageable pageable) {
        List<Album> allAlbums = albumRepository.findAll(pageable);
        return allAlbums.stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                .toList();
    }

    AlbumDtoWithSongs findAlbumByIdWithSongs(final Long id) {
        Album album = albumRepository.findAlbumByIdWithSongs(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with id: " + id + " not found"));

        Set<SongDto> songDto = album.getSongs().stream()
                .map(song -> new SongDto(song.getId(), song.getName(), song.getGenre().getName()))
                .collect(Collectors.toSet());

        return new AlbumDtoWithSongs(songDto);
    }

    List<AlbumDto> getAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAllAlbumsByArtistId(artistId).stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                .collect(Collectors.toList());
    }
}