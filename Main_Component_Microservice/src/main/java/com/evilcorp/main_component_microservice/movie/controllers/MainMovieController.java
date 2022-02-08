package com.evilcorp.main_component_microservice.movie.controllers;

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

    private final MovieMainService movieMainService;
    private final ExternalApiService externalApiService;

    @GetMapping(value = "/translate/{text}")
    public ResponseEntity<?> getTranslation(@PathVariable String text){
        String translation = externalApiService.translateTextTest(text);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(translation);
    }

    @GetMapping(value = "/{movieId}",
            produces = "application/json")
    public ResponseEntity<?> getMovie(@PathVariable UUID movieId){
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
        if(movieMainService.createMovie(newMovie)){
            return ResponseEntity
                    .ok( linkTo(methodOn(MainMovieController.class).getMovie(newMovie.getId())).withSelfRel() );
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Movie could not be created!");
    }

    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity<?> updateMovie(@Valid @RequestBody Movie changedMovie){
        if(movieMainService.updateMovie(changedMovie)){
            return ResponseEntity
                    .ok( linkTo(methodOn(MainMovieController.class).getMovie(changedMovie.getId())).withSelfRel() );
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Movie could not be updated!");
    }

    @DeleteMapping(value = "/delete/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable UUID movieId){
        if(movieMainService.deleteMovie(movieId)){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Movie could not be deleted!");
    }
}