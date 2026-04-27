package com.example.Movie_Reservation_System.service;

import com.example.Movie_Reservation_System.Enums.SeatType;
import com.example.Movie_Reservation_System.entity.Screen;
import com.example.Movie_Reservation_System.entity.Seat;
import com.example.Movie_Reservation_System.repository.ScreenRepository;
import com.example.Movie_Reservation_System.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatService(SeatRepository seatRepository,
                       ScreenRepository screenRepository) {
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }

    // Create seats for a screen
    public List<Seat> createSeats(Long screenId, int rows, int cols) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            char rowChar = (char) ('A' + i);

            for (int j = 1; j <= cols; j++) {
                Seat seat = new Seat();
                seat.setSeatNumber(rowChar + String.valueOf(j));

                seat.setScreen(screen);
                if(i<1)
                {
                    seat.setType(SeatType.VIP);
                    seat.setPrice(250.0);
                }
                else if(i<3)
                {
                    seat.setType(SeatType.PREMIUM);
                    seat.setPrice(200.0);
                }
                else {
                    seat.setType(SeatType.REGULAR);
                    seat.setPrice(150.0);
                }
                seats.add(seat);
            }
        }

        return seatRepository.saveAll(seats);
    }

    public List<Seat> getSeatsByScreen(Long screenId) {
        return seatRepository.findByScreenId(screenId);
    }
}
