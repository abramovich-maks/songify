package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.GetSongDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class SongViewController {

    private final SongifyCrudFacade songifyCrudFacade;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/view/songs")
    public String songs(Model model) {
        List<SongDto> songs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
        model.addAttribute("songs", songs);
        return "song";
    }

    @GetMapping("/view/songs/{songId}")
    public String songById(Model model, @PathVariable Long songId) {
        GetSongDto songDtoById = songifyCrudFacade.findSongDtoById(songId);
        model.addAttribute("songs", songDtoById);
        return "songById";
    }
}
