package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.dto.SeatResponse;
import com.example.Movie_Reservation_System.entity.Seat;
import com.example.Movie_Reservation_System.entity.Show;
import com.example.Movie_Reservation_System.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public Show createShow(@RequestParam Long movieId,
                           @RequestParam Long screenId,
                           @RequestParam String showTime) {

        LocalDateTime time = LocalDateTime.parse(showTime);
        return showService.createShow(movieId, screenId, time);
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable Long movieId) {
        return showService.getShowsByMovie(movieId);
    }

    @GetMapping("/{showId}/available-seats")
    public List<Seat> getAvailableSeats(@PathVariable Long showId) {
        return showService.getAvailableSeats(showId);
    }

    @GetMapping("/{showId}/seats")
    public List<SeatResponse> getSeats(@PathVariable Long showId) {
        return showService.getSeatsForShow(showId);
    }

}
