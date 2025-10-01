package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
public class SongifyCrudFasade {
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;

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

    public List<SongDto> findAll(final Pageable pageable) {
        return songRetriever.findAll(pageable)
                .stream().map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public SongDto findById(Long id) {
        SongEntity song = songRetriever.findSongById(id);
        return SongDto.builder()
                .id(song.getId())
                .name(song.getName())
                .build();
    }

    public void deleteById(Long id) {
        songRetriever.existById(id);
        songDeleter.deleteById(id);
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
