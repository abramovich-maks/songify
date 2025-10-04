package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistWithAlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ArtistRetriever {

    private final ArtistRepository artistRepository;

    Set<ArtistDto> findAllArtists(final Pageable pageable) {
        return artistRepository.findAll(pageable)
                .stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                .collect(Collectors.toSet());
    }

    Artist findById(final Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ArtistNotFoundExceptions("Artist with id: " + artistId + " not found"));
    }


    ArtistWithAlbumDto findArtistByIdWithAlbum(final Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundExceptions("Artist with id: " + id + " not found"));

        ArtistDto artistDto = new ArtistDto(artist.getId(), artist.getName());

        List<AlbumDto> albums = artist.getAlbums().stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                .toList();

        return new ArtistWithAlbumDto(artistDto, albums);
    }
}
