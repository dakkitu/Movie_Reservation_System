package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.entity.Theater;
import com.example.Movie_Reservation_System.service.TheaterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    public Theater addTheater(@RequestBody Theater theater) {
        return theaterService.addTheater(theater);
    }

    @GetMapping
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }
}
