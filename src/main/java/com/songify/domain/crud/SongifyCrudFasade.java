package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistAndSongs;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.ArtistWithAlbumDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreDtoWithSongsAndArtist;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.GetSongDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.domain.crud.dto.UpdateAlbumRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
public class SongifyCrudFasade {
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final SongAssigner songAssigner;
    private final GenreAdder genreAdder;
    private final GenreRetriever genreRetriever;
    private final GenreUpdater genreUpdater;
    private final AlbumAdder albumAdder;
    private final AlbumRetriever albumRetriever;
    private final AlbumDeleter albumDeleter;
    private final AlbumUpdater albumUpdater;
    private final ArtistAdder artistAdder;
    private final ArtistRetriever artistRetriever;
    private final ArtisDeleter artisDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;

    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public AlbumDto addAlbumWithSong(AlbumRequestDto dto) {
        return albumAdder.addAlbum(dto.songId(), dto.title(), dto.releaseDate());
    }

    public SongDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public Set<ArtistDto> findAllArtists(final Pageable pageable) {
        return artistRetriever.findAllArtists(pageable);
    }

    public List<SongDto> findAllSongs(final Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public List<AlbumDto> findAllAlbums(final Pageable pageable) {
        return albumRetriever.findAll(pageable);
    }

    public Set<GenreDto> findAllGenres(final Pageable pageable) {
        return genreRetriever.findAll(pageable);
    }

    public GenreDtoWithSongsAndArtist findGenreByIdWithAllSongsAndArtist(final Long id) {
        return genreRetriever.findGenreByIdWithAllSongsAndArtist(id);
    }

    public GetSongDto findSongDtoById(Long id) {
        return songRetriever.findSongDtoById(id);
    }

    public ArtistWithAlbumDto findArtistByIdWithAlbum(Long id) {
        return artistRetriever.findArtistByIdWithAlbum(id);
    }

    public AlbumDtoWithArtistAndSongs findAlbumByIdWithArtistAndSong(final Long id) {
        return albumRetriever.findAlbumByIdWithArtistAndSongs(id);
    }

    public void deleteSongById(Long id) {
        songDeleter.deleteById(id);
    }

    public void deleteAlbumById(Long albumId) {
        albumDeleter.deleteById(albumId);
    }

    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        artisDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);
    }

    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public void addSongToArtist(Long songId, Long artistId) {
        songAssigner.addSongToArtist(songId, artistId);
    }

    public void addSongToAlbum(Long songId, Long albumId) {
        songAssigner.addSongToAlbum(songId, albumId);
    }

    public void addSongToGenre(Long songId, Long genreId) {
        songAssigner.addGenreToSong(songId, genreId);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSing(ArtistRequestDto dto) {
        return artistAdder.addArtistWithDefaultAlbumAndSong(dto);
    }

    public ArtistDto updateArtistNameById(Long artistId, String name) {
        return artistUpdater.updateArtistNameById(artistId, name);
    }

    public GenreDto updateGenreById(Long genreId, String newGenre) {
        return genreUpdater.updateGenreById(genreId, newGenre);
    }

    public void updatePartiallyById(Long id, SongRequestDto songFromRequest) {
        songUpdater.updatePartiallyById(id, songFromRequest);
    }

    public void addSongToAlbumById(Long albumId, Long songId) {
        albumUpdater.addSongToAlbumById(albumId, songId);
    }

    public void deleteSongFromAlbumById(Long albumId, Long songId) {
        albumUpdater.deleteSongFromAlbumById(albumId, songId);
    }

    public void addArtistToAlbumById(Long albumId, Long artistId) {
        albumUpdater.addArtistToAlbumById(albumId, artistId);
    }

    public void deleteArtistFromAlbumById(Long albumId, Long artistId) {
        albumUpdater.deleteArtistFromAlbumById(albumId, artistId);

    }

    public void updateAlbumNameOrReleaseById(Long albumId, UpdateAlbumRequestDto dto) {
        albumUpdater.updateAlbumNameOrReleaseById(albumId, dto);
    }

    public List<AlbumDto> getAlbumsByArtistId(Long artistId) {
        return albumRetriever.getAlbumsByArtistId(artistId);
    }

    int countArtistByAlbumId(final Long albumId) {
        return albumRetriever.countArtistByAlbumId(albumId);
    }

    AlbumDto findAlbumById(final Long albumId) {
        return albumRetriever.findDtoById(albumId);
    }

    GenreDto findGenreById(Long genreId) {
        return genreRetriever.findGenreById(genreId);
    }
}