package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater,Long> {
}
