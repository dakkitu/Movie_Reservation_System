package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.SeatLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {
    List<SeatLock> findBySeatIdIn(List<Long> seatIds);

    void deleteBySeatIdInAndUserId(List <Long>  seatIds,String userId);

    @Query("""
    SELECT sl.seatId FROM SeatLock sl
    WHERE sl.showId = :showId
    AND sl.lockTime > :expiryTime
""")
    List<Long> findLockedSeatIds(Long showId, LocalDateTime expiryTime);
}
