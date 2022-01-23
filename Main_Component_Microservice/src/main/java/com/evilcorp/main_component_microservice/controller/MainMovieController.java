package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.services.MainMovieLogicService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
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

    private final MainMovieLogicService mainMovieLogicService;

    @GetMapping(value = "/{movieId}",
            produces = "application/json")
    public ResponseEntity getMovie(@PathVariable UUID movieId){
        Film responseMovie = mainMovieLogicService.getMovie(movieId);
        return ResponseEntity.status(HttpStatus.FOUND).body(responseMovie);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Film>> getAllMovies(){
        List<Film> responseMovies = mainMovieLogicService.getAllMovies();
        return ResponseEntity.status(HttpStatus.FOUND).body(responseMovies);
    }

    @PostMapping(value = "/create",
            consumes = "application/json")
    public ResponseEntity createMovie(@RequestBody @Valid Film newMovie){
        if(mainMovieLogicService.createMovie(newMovie)){
            return ResponseEntity.ok(
                    linkTo(
                            methodOn(MainMovieController.class)
                                    .getMovie(newMovie.getId()))
                            .withSelfRel());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie could not be created!");
    }

    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity updateMovie(@Valid @RequestBody Film changedMovie){
        if(mainMovieLogicService.updateMovie(changedMovie)){
            return ResponseEntity.ok(
                    linkTo(
                            methodOn(MainMovieController.class)
                                    .getMovie(changedMovie.getId()))
                            .withSelfRel());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie could not be updated!");
    }

    @DeleteMapping(value = "/delete/{movieId}")
    public ResponseEntity deleteMovie(@PathVariable UUID movieId){
        if(mainMovieLogicService.deleteMovie(movieId)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie could not be deleted!");
    }
}