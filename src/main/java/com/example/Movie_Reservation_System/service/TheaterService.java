package com.example.Movie_Reservation_System.service;

import com.example.Movie_Reservation_System.entity.Theater;
import com.example.Movie_Reservation_System.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public Theater addTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }
}
