package com.example.Movie_Reservation_System.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean processPayment(Long bookingId, double amount) {

        // simulate real payment delay
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // simulate success/failure
        return Math.random() > 0.9; // 70% success
    }
}
