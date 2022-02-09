package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers;

import com.evilcorp.main_component_microservice.ParserService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.SimpleMovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.services.RatingService;
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

    private final ParserService parserService;
    private final RatingService ratingService;

    @GetMapping(value = "/{ratingIdString}",
        produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRatingRepresentation> getMovieRating(@PathVariable String ratingIdString){
        UUID ratingId = parserService.parseStringToUUID(ratingIdString);
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
                .body(ratingsRepresentation);
    }

    @GetMapping(value = "/of_user/{userIdString}",
            produces = "application/json")
    public ResponseEntity<CollectionModel<MovieRatingRepresentation>> getAllMovieRatingsOfUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        CollectionModel<MovieRatingRepresentation> ratingRepresentations = ratingService.getAllMovieRatingsOfUserByRepo(userId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(ratingRepresentations);
    }

    @PostMapping(value = "/create",
            produces = "application/json")
    public ResponseEntity<Link> rateMovie(@RequestBody SimpleMovieRating newRating){
        Link linkToNewMovieRating = ratingService.rateMovie(newRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkToNewMovieRating );
    }

    @PutMapping(value = "/update/{ratingIdString}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> updateRating(@PathVariable String ratingIdString, @RequestBody int newRatingValue){
        UUID ratingId = parserService.parseStringToUUID(ratingIdString);
        Link linkToUpdatedMovieRating = ratingService.updateRating(ratingId, newRatingValue);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(linkToUpdatedMovieRating);
    }

    @DeleteMapping(value = "/delete/{ratingIdString}")
    public ResponseEntity<?> deleteRating(@PathVariable String ratingIdString){
        UUID ratingId = parserService.parseStringToUUID(ratingIdString);
        ratingService.deleteRating(ratingId);
        return ResponseEntity
                .noContent()
                .build();
    }
}