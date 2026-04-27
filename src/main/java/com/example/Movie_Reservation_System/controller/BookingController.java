package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.dto.BookingRequest;
import com.example.Movie_Reservation_System.entity.Booking;
import com.example.Movie_Reservation_System.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking book(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @PostMapping("/payment/{bookingId}")
    public ResponseEntity<Booking> processPayment(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.processPayment(bookingId));
    }
}
