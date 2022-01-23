package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/ratings")
public class MainRatingController{

    private final RatingService ratingService;

    @GetMapping(value = "/{ratingId}",
        produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRatingRepresentation> getMovieRating(@PathVariable UUID ratingId){
        MovieRatingRepresentation ratingRepresentation = ratingService.getMovieRating(ratingId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(ratingRepresentation);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<MovieRatingRepresentation>> getAllMovieRatings(){
        CollectionModel<MovieRatingRepresentation> ratingsRepresentation = ratingService.getAllMovieRatings();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body( ratingsRepresentation );
    }

    @PostMapping(value = "/create",
            produces = "application/json")
    public ResponseEntity<Link> rateMovie(@RequestBody MovieRating newRating){
        Link linkToNewMovieRating = ratingService.rateMovie(newRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkToNewMovieRating );
    }

    @PutMapping(value = "/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> updateRating(@RequestBody MovieRating newRating){
        Link linkToUpdatedMovieRating = ratingService.updateRating(newRating);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(linkToUpdatedMovieRating);
    }

    @DeleteMapping(value = "/delete/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable UUID ratingId){
        ratingService.deleteRating(ratingId);
        return ResponseEntity
                .noContent()
                .build();
    }
}