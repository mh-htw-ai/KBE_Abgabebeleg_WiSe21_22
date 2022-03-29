package com.evilcorp.main_component_microservice.movie.controllers;

import com.evilcorp.main_component_microservice.parsing.ParserService;
import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.movie.services.MovieMainService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MainMovieController{

    private final ParserService parserService;
    private final MovieMainService movieMainService;

    @GetMapping(value = "/{movieIdString}",
            produces = "application/json")
    public ResponseEntity<?> getMovie(@PathVariable String movieIdString){
        UUID movieId = parserService.parseStringToUUID(movieIdString);
        Movie responseMovie = movieMainService.getMovie(movieId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMovie);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> responseMovies = movieMainService.getAllMovies();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseMovies);
    }

    @PostMapping(value = "/create",
            consumes = "application/json")
    public ResponseEntity<?> createMovie(@RequestBody @Valid Movie newMovie){
        movieMainService.createMovie(newMovie);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newMovie.getId());
    }

    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity<UUID> updateMovie(@Valid @RequestBody Movie changedMovie){
        movieMainService.updateMovie(changedMovie);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(changedMovie.getId());
    }

    @DeleteMapping(value = "/delete/{movieIdString}")
    public ResponseEntity<?> deleteMovie(@PathVariable String movieIdString){
        UUID movieId = parserService.parseStringToUUID(movieIdString);
        movieMainService.deleteMovie(movieId);
        return ResponseEntity
                .noContent()
                .build();
    }
}