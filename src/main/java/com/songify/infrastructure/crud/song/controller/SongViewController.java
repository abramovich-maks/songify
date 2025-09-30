package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongViewController {
    private final SongRepository repository;

    public SongViewController(SongRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/view/songs")
    public String songs(Model model) {
//        List<SongControllerResponseDto> songs = repository.findAll(Pageable.unpaged());
//        model.addAttribute("songs", songs);
        return "song";
    }
}
