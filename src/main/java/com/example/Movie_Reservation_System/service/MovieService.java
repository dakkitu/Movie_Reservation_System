package com.example.Movie_Reservation_System.service;

import com.example.Movie_Reservation_System.entity.Movie;
import com.example.Movie_Reservation_System.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Add movie
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Get all movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get movie by id
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }
}
