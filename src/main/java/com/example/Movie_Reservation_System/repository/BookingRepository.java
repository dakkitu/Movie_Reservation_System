package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
