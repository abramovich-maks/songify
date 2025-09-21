package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        List<SongEntity> songs = repository.findAll(Pageable.unpaged());
        model.addAttribute("songs", songs);
        return "song";
    }
}
