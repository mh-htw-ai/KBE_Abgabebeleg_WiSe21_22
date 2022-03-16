package com.evilcorp.main_component_microservice.movie.controllers;

import com.evilcorp.main_component_microservice.parsing.ParserService;
import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.movie.services.MovieMainService;
import com.evilcorp.main_component_microservice.movie.services.external_api_service.ExternalApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/movies")
public class MainMovieController{

    private final ParserService parserService;
    private final MovieMainService movieMainService;
    private final ExternalApiService externalApiService;


    @GetMapping(value = "/translate/{text}")
    public ResponseEntity<?> getTranslation(@PathVariable String text){
        String translation = externalApiService.translateTextTest(text);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(translation);
    }

    @GetMapping(value = "/{movieIdString}",
            produces = "application/json")
    public ResponseEntity<?> getMovie(@PathVariable String movieIdString){
        UUID movieId = parserService.parseStringToUUID(movieIdString);
        Movie responseMovie = movieMainService.getMovie(movieId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(responseMovie);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> responseMovies = movieMainService.getAllMovies();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(responseMovies);
    }

    @PostMapping(value = "/create",
            consumes = "application/json")
    public ResponseEntity<?> createMovie(@RequestBody @Valid Movie newMovie){
        movieMainService.createMovie(newMovie);
        return ResponseEntity
                .ok( linkTo(methodOn(MainMovieController.class).getMovie(newMovie.getId().toString())).withSelfRel() );
    }

    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity<?> updateMovie(@Valid @RequestBody Movie changedMovie){
        movieMainService.updateMovie(changedMovie);
        return ResponseEntity
                .ok( linkTo(methodOn(MainMovieController.class).getMovie(changedMovie.getId().toString())).withSelfRel() );
    }

    @DeleteMapping(value = "/delete/{movieIdString}")
    public ResponseEntity<?> deleteMovie(@PathVariable String movieIdString){
        UUID movieId = parserService.parseStringToUUID(movieIdString);
        movieMainService.deleteMovie(movieId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}