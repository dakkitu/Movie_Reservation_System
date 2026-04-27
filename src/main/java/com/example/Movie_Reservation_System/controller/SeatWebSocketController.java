package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.dto.SeatUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SeatWebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendSeatUpdate(Long showId, Long seatId, String status) {

        SeatUpdate update = new SeatUpdate();
        update.setSeatId(seatId);
        update.setStatus(status);

        messagingTemplate.convertAndSend(
                "/topic/seats/" + showId,
                update
        );
    }
}
