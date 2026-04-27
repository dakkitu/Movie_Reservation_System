package com.example.Movie_Reservation_System.entity;
import jakarta.persistence.*;
        import lombok.Data;

@Entity
@Data
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Theater theater;
}
