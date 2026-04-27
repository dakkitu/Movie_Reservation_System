package com.example.Movie_Reservation_System.service;

import com.example.Movie_Reservation_System.entity.Screen;
import com.example.Movie_Reservation_System.entity.Theater;
import com.example.Movie_Reservation_System.repository.ScreenRepository;
import com.example.Movie_Reservation_System.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final TheaterRepository theaterRepository;

    public ScreenService(ScreenRepository screenRepository,
                         TheaterRepository theaterRepository) {
        this.screenRepository = screenRepository;
        this.theaterRepository = theaterRepository;
    }

    public Screen addScreen(Long theaterId, Screen screen) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        screen.setTheater(theater);
        return screenRepository.save(screen);
    }

    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }
}
