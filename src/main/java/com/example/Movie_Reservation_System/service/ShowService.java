package com.example.Movie_Reservation_System.service;

import com.example.Movie_Reservation_System.dto.SeatResponse;
import com.example.Movie_Reservation_System.entity.*;
import com.example.Movie_Reservation_System.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatLockRepository seatLockRepository;
    public ShowService(ShowRepository showRepository,
                       MovieRepository movieRepository,
                       ScreenRepository screenRepository, SeatRepository seatRepository, BookingSeatRepository bookingSeatRepository, SeatLockRepository seatLockRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.seatLockRepository = seatLockRepository;
    }

    public Show createShow(Long movieId, Long screenId, LocalDateTime showTime) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setShowTime(showTime);

        return showRepository.save(show);
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public List<Seat> getAvailableSeats(Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));


        List<Seat> allSeats = seatRepository.findByScreenId(show.getScreen().getId());

        List<Long> seatIds = allSeats.stream()
                .map(Seat::getId)
                .toList();

        List<BookingSeat> bookedSeats =
                bookingSeatRepository.findBookedSeats(showId, seatIds);

        List<Long> bookedSeatIds = bookedSeats.stream()
                .map(BookingSeat::getSeatId)
                .toList();

        return allSeats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .toList();
    }

    public List<SeatResponse> getSeatsForShow(Long showId) {

        Show show = showRepository.findById(showId).orElseThrow();

        List<Seat> seats = seatRepository.findByScreenId(show.getScreen().getId());

        List<Long> bookedSeatIds =
                bookingSeatRepository.findBookedSeatIds(showId);
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(5);
        List<Long> lockedSeatIds =
                seatLockRepository.findLockedSeatIds(showId,expiryTime);

        return seats.stream().map(seat -> {

            String status = "AVAILABLE";

            if (bookedSeatIds.contains(seat.getId())) {
                status = "BOOKED";
            } else if (lockedSeatIds.contains(seat.getId())) {
                status = "LOCKED";
            }

            return new SeatResponse(seat.getId(), status,seat.getSeatNumber());
        }).toList();
    }
}
