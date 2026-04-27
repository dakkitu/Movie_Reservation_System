package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.entity.Screen;
import com.example.Movie_Reservation_System.service.ScreenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/screens")
public class ScreenController {
    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping("/{theaterId}")
    public Screen addScreen(@PathVariable Long theaterId,
                            @RequestBody Screen screen) {
        return screenService.addScreen(theaterId, screen);
    }

    @GetMapping
    public List<Screen> getAllScreens() {
        return screenService.getAllScreens();
    }
}
