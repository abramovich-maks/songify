package com.songify.domain.songplayer;

import com.songify.domain.crud.dto.GetSongDto;

public class SongifyCrudFacade {

    private final com.songify.domain.crud.SongifyCrudFacade songifyCrudFacade;
    private final YoutubeHttpClient youtubeHttpClient;


    public SongifyCrudFacade(final com.songify.domain.crud.SongifyCrudFacade songifyCrudFacade, final YoutubeHttpClient youtubeHttpClient) {
        this.songifyCrudFacade = songifyCrudFacade;
        this.youtubeHttpClient = youtubeHttpClient;
    }

    public String playSongWithId(Long id) {
        GetSongDto songDtoById = songifyCrudFacade.findSongDtoById(id);
        String name = songDtoById.name();
        String result = youtubeHttpClient.playSongByName(name);
        if (result.equals("success")) {
            return result;
        }
        throw new RuntimeException("some error - result failed");
    }
}
