package com.songify;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    Map<Integer, String> dataBase = new HashMap<>(Map.of(
            1, "ShawnMendes songs1",
            2, "ArianaGrande songs2",
            3, "Crimsonsun song3",
            4, "Letdown"));

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/view/songs")
    public String songs(Model model) {
        model.addAttribute("songMap", dataBase);
        return "song";
    }
}
