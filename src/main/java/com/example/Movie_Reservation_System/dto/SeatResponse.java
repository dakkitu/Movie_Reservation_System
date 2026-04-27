package com.example.Movie_Reservation_System.dto;

import lombok.Data;

@Data
public class SeatResponse {
    private Long seatId;
    private String status;
    private String seatNumber;
    public SeatResponse(Long seatId, String status,String seatNumber) {
        this.seatId = seatId;
        this.status = status;
        this.seatNumber =seatNumber;
    }
}
