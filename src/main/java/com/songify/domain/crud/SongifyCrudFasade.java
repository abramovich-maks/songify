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
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
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

    public SongDto findSongDtoById(Long id) {
        return songRetriever.findSongDtoById(id);
    }

    public ArtistWithAlbumDto findArtistByIdWithAlbum(Long id) {
        return artistRetriever.findArtistByIdWithAlbum(id);
    }

    public AlbumDtoWithArtistAndSongs findAlbumByIdWithArtistAndSong(final Long id) {
        return albumRetriever.findAlbumByIdWithArtistAndSongs(id);
    }

    public void deleteSongById(Long id) {
        songRetriever.existById(id);
        songDeleter.deleteById(id);
    }

    public void deleteAlbumById(Long albumId) {
        AlbumDtoWithSongs album = albumRetriever.findAlbumByIdWithSongs(albumId);
        if (!album.songs().isEmpty()) {
            throw new AlbumNotEmptyException("Album with id: " + albumId + " contains songs and cannot be deleted");
        }
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

    public SongDto updatePartiallyById(Long id, SongRequestDto songFromRequest) {
        songRetriever.existById(id);
        SongEntity songFromDatabase = songRetriever.findSongById(id);
        SongEntity toSave = SongDomainMapper.mapFromPartiallyUpdateSongRequestDtoToSong(
                new PartiallyUpdateSongRequestDto(songFromRequest.name())
        );
        if (songFromRequest.name() != null) {
            toSave.setName(songFromRequest.name());
        } else {
            toSave.setName(songFromDatabase.getName());
        }
        songUpdater.updateById(id, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .build();
    }

    public void updateById(Long id, SongRequestDto newSongDto) {
        songRetriever.existById(id);
        SongEntity entityToUpdate = SongDomainMapper.mapFromUpdateSongRequestDtoToSong(
                new UpdateSongRequestDto(newSongDto.name())
        );
        songUpdater.updateById(id, entityToUpdate);
    }
}
