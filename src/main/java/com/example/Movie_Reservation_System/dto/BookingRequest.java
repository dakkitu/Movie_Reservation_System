package com.example.Movie_Reservation_System.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private Long showId;
    private List<Long> seatIds;
    private String userId;
}
