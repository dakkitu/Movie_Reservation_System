package com.example.Movie_Reservation_System.service;

import com.example.Movie_Reservation_System.Enums.BookingStatus;
import com.example.Movie_Reservation_System.controller.SeatWebSocketController;
import com.example.Movie_Reservation_System.dto.BookingRequest;
import com.example.Movie_Reservation_System.entity.*;
import com.example.Movie_Reservation_System.repository.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    @Autowired
    private SeatLockRepository seatLockRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SeatWebSocketController socketController;
    public BookingService(BookingRepository bookingRepository,
                          BookingSeatRepository bookingSeatRepository,
                          ShowRepository showRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Booking createBooking(BookingRequest request) {

        // 🔥 STEP 0: Remove expired locks
        removeExpiredLocks();

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // 🔥 STEP 1: Check already booked seats
        List<BookingSeat> bookedSeats =
                bookingSeatRepository.findBookedSeats(
                        request.getShowId(),
                        request.getSeatIds()
                );

        if (!bookedSeats.isEmpty()) {
            throw new RuntimeException("Some seats already booked!");
        }

        // 🔥 STEP 2: Check if seats are locked by someone else
        List<SeatLock> locks = seatLockRepository.findBySeatIdIn(request.getSeatIds());

        boolean lockedByOtherUser = locks.stream()
                .anyMatch(lock -> !lock.getUserId().equals(request.getUserId()));

        if (lockedByOtherUser) {
            throw new RuntimeException("Some seats are currently locked!");
        }

        // 🔥 STEP 3: Lock seats for current user
        lockSeats(request.getSeatIds(), request.getUserId(),request.getShowId());
        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());
        if (seats.size() != request.getSeatIds().size()) {
            throw new RuntimeException("Invalid seat selection");
        }

        // 🔥 Calculate total price
        double totalAmount = seats.stream()
                .mapToDouble(Seat::getPrice)
                .sum();
        // 🔥 STEP 4: Create booking
        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUserId(request.getUserId()); // ✅ important
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalPrice(totalAmount);
        booking = bookingRepository.save(booking);

        // 🔥 STEP 5: Save booked seats
        for (Seat seat : seats) {
            BookingSeat bs = new BookingSeat();
            bs.setBooking(booking);
            bs.setSeatId(seat.getId());

            bookingSeatRepository.save(bs);
        }

        return booking;
    }

    private void lockSeats(List<Long> seatIds, String userId,Long showId) {

        List<SeatLock> locks = seatIds.stream().map(seatId -> {
            SeatLock lock = new SeatLock();
            lock.setSeatId(seatId);
            lock.setUserId(userId);
            lock.setShowId(showId);
            lock.setLockTime(LocalDateTime.now());
            lock.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            // 🔥 SEND REAL-TIME UPDATE
            socketController.sendSeatUpdate(showId, seatId, "LOCKED");
            return lock;
        }).toList();

        seatLockRepository.saveAll(locks);
    }
    public void removeExpiredLocks() {
        List<SeatLock> locks = seatLockRepository.findAll();

        List<SeatLock> expired = locks.stream()
                .filter(lock -> lock.getExpiryTime().isBefore(LocalDateTime.now()))
                .toList();

        seatLockRepository.deleteAll(expired);
    }

    @Transactional
    public Booking processPayment(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String userId=booking.getUserId();
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Invalid booking state");
        }

        boolean paymentSuccess = paymentService.processPayment(
                bookingId,
                booking.getTotalPrice()
        );

        List<Long> seatIds = booking.getBookingSeats()
                .stream()
                .map(BookingSeat::getSeatId)
                .toList();

        if (paymentSuccess) {
            booking.setStatus(BookingStatus.CONFIRMED);
            for (Long seatId : seatIds) {
                socketController.sendSeatUpdate(booking.getShow().getId(), seatId, "BOOKED");
            }

        } else {
            booking.setStatus(BookingStatus.FAILED);
            for (Long seatId : seatIds) {
                socketController.sendSeatUpdate(booking.getShow().getId(), seatId, "AVAILABLE");
            }
        }
        // 🔥 Release locks on failure
        seatLockRepository.deleteBySeatIdInAndUserId(seatIds,userId);
        return bookingRepository.save(booking);
    }
}
