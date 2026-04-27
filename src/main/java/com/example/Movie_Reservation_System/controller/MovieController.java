package com.example.Movie_Reservation_System.controller;

import com.example.Movie_Reservation_System.entity.Movie;
import com.example.Movie_Reservation_System.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // POST: Add movie
    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    // GET: All movies
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // GET: Movie by ID
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }
}
