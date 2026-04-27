package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.entity.Seat;
import com.example.Movie_Reservation_System.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/seats")

public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Create seats
    @PostMapping("/{screenId}")
    public List<Seat> createSeats(@PathVariable Long screenId,
                                  @RequestParam int rows,
                                  @RequestParam int cols) {

        return seatService.createSeats(screenId, rows, cols);
    }

    // Get seats
    @GetMapping("/{screenId}")
    public List<Seat> getSeats(@PathVariable Long screenId) {
        return seatService.getSeatsByScreen(screenId);
    }
}
