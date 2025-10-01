package com.songify.domain.crud.dto;


import java.util.Set;

public interface AlbumInfo {
    Long getId();

    Set<SongInfo> getSongs();

    interface SongInfo {
        Long getId();

        String getName();

        GenreInfo getGenre();

        interface GenreInfo {
            Long getId();

            String getName();
        }
    }

    interface ArtistInfo {
        Long getId();

        String getName();
    }
}