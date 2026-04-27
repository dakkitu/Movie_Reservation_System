package com.example.Movie_Reservation_System.dto;

import lombok.Data;

@Data
public class SeatUpdate {
    private Long seatId;
    private String status; // LOCKED / BOOKED
}
