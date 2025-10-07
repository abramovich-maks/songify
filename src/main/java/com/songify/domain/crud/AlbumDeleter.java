package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
class AlbumDeleter {

    private final AlbumRepository albumRepository;
    private final AlbumRetriever albumRetriever;

    void deleteAllAlbumsByIds(final Set<Long> albumsIdsToDelete) {
        albumRepository.deleteByIdIn(albumsIdsToDelete);
    }

    void deleteById(final Long albumId) {
        AlbumDtoWithSongs album = albumRetriever.findAlbumByIdWithSongs(albumId);
        if (!album.songs().isEmpty()) {
            throw new AlbumNotEmptyException("Album with id: " + albumId + " contains songs and cannot be deleted");
        }
        albumRepository.deleteById(albumId);
    }
}
