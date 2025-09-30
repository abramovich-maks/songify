package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.UpdateSongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SongCrudFasade {
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public List<SongDto> findAll(final Pageable pageable) {
        return songRetriever.findAll(pageable)
                .stream().map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public SongDto findById(Long id) {
        SongEntity song = songRetriever.findSongDtoById(id);
        return SongDto.builder()
                .id(song.getId())
                .name(song.getName())
                .artist(song.getArtist())
                .build();
    }

    public SongDto addSong(final SongDto songDto) {
        SongEntity entityToSave = SongDomainMapper.mapFromCreateSongRequestDtoToSong(
                new CreateSongRequestDto(songDto.name(), songDto.artist())
        );
        SongEntity addedSong = songAdder.addSong(entityToSave);

        return SongDto.builder()
                .id(addedSong.getId())
                .name(addedSong.getName())
                .artist(addedSong.getArtist())
                .build();
    }

    public void deleteById(Long id) {
        songRetriever.existById(id);
        songDeleter.deleteById(id);
    }

    public SongDto updatePartiallyById(Long id, SongDto songFromRequest) {
        songRetriever.existById(id);
        SongEntity songFromDatabase = songRetriever.findSongDtoById(id);
        SongEntity toSave = SongDomainMapper.mapFromPartiallyUpdateSongRequestDtoToSong(
                new PartiallyUpdateSongRequestDto(songFromRequest.name(), songFromRequest.artist())
        );
        if (songFromRequest.name() != null) {
            toSave.setName(songFromRequest.name());
        } else {
            toSave.setName(songFromDatabase.getName());
        }
        if (songFromRequest.artist() != null) {
            toSave.setArtist(songFromRequest.artist());
        } else {
            toSave.setArtist(songFromDatabase.getArtist());
        }
        songUpdater.updateById(id, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .artist(toSave.getArtist())
                .build();
    }

    public void updateById(Long id, SongDto newSongDto) {
        songRetriever.existById(id);
        SongEntity entityToUpdate = SongDomainMapper.mapFromUpdateSongRequestDtoToSong(
                new UpdateSongRequestDto(newSongDto.name(), newSongDto.artist())
        );
        songUpdater.updateById(id, entityToUpdate);
    }
}
