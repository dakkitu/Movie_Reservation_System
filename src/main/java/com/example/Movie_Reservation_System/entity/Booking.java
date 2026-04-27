package com.example.Movie_Reservation_System.entity;

import com.example.Movie_Reservation_System.Enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Show show;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private Double totalPrice;

    @OneToMany(mappedBy = "booking")
    private List<BookingSeat> bookingSeats;

    private String userId;

}
