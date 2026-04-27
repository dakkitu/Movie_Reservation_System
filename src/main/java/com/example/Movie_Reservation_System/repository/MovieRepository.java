package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
