package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findByMovieId(Long movieId);
}
