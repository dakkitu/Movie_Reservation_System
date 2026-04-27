package com.example.Movie_Reservation_System.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;

    private java.time.LocalDateTime showTime;

}
