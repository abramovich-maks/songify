package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.SongDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SongViewController {

    SongifyCrudFasade songifyCrudFasade;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/view/songs")
    public String songs(Model model) {
        List<SongDto> songs = songifyCrudFasade.findAll(Pageable.unpaged());
        model.addAttribute("songs", songs);
        return "song";
    }
}
