package com.example.Movie_Reservation_System.repository;

import com.example.Movie_Reservation_System.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {

}
