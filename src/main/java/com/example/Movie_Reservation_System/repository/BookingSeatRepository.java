package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingSeatRepository extends JpaRepository<BookingSeat,Long> {
    @Query("SELECT bs FROM BookingSeat bs WHERE bs.seatId IN :seatIds AND bs.booking.show.id = :showId AND bs.booking.status = 'CONFIRMED'")
    List<BookingSeat> findBookedSeats(Long showId, List<Long> seatIds);

    @Query("""
    SELECT bs.seatId FROM BookingSeat bs
    WHERE bs.booking.show.id = :showId
    AND bs.booking.status = 'CONFIRMED'
""")
    List<Long> findBookedSeatIds(Long showId);


}
