package com.songify.domain.songplayer;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.GetSongDto;

public class SongPlayerFasade {

    private final SongifyCrudFasade songifyCrudFasade;
    private final YoutubeHttpClient youtubeHttpClient;


    public SongPlayerFasade(final SongifyCrudFasade songifyCrudFasade, final YoutubeHttpClient youtubeHttpClient) {
        this.songifyCrudFasade = songifyCrudFasade;
        this.youtubeHttpClient = youtubeHttpClient;
    }

    public String playSongWithId(Long id) {
        GetSongDto songDtoById = songifyCrudFasade.findSongDtoById(id);
        String name = songDtoById.name();
        String result = youtubeHttpClient.playSongByName(name);
        if (result.equals("success")) {
            return result;
        }
        throw new RuntimeException("some error - result failed");
    }
}
