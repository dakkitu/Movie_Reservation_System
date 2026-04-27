package com.example.Movie_Reservation_System.entity;

import com.example.Movie_Reservation_System.Enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // A1, A2...

    @Enumerated(EnumType.STRING)
    private SeatType type; // REGULAR, VIP

    private Double price;

    @ManyToOne
    private Screen screen;
}
