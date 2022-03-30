package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers;

import com.evilcorp.main_component_microservice.parsing.ParserService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.SimpleMovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<MovieRating> getMovieRating(@PathVariable String ratingIdString){
        UUID ratingId = parserService.parseStringToUUID(ratingIdString);
        MovieRating rating = ratingService.getMovieRating(ratingId);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( rating );
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MovieRating>> getAllMovieRatings(){
        List<MovieRating> ratings = ratingService.getAllMovieRatings();
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( ratings );
    }

    @GetMapping(value = "/of_user/{userIdString}",
            produces = "application/json")
    public ResponseEntity<List<MovieRating>> getAllMovieRatingsOfUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        List<MovieRating> ratings = ratingService.getAllMovieRatingsOfUserByRepo(userId);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( ratings );
    }

    @PostMapping(value = "/create",
            produces = "application/json")
    public ResponseEntity<UUID> rateMovie(@RequestBody SimpleMovieRating newRating){
        UUID ratingId = ratingService.rateMovie(newRating);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( ratingId );
    }

    @PutMapping(value = "/update/{ratingIdString}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<MovieRating> updateRating(@PathVariable String ratingIdString, @RequestBody int newRatingValue){
        UUID ratingId = parserService.parseStringToUUID(ratingIdString);
        MovieRating updatedRating = ratingService.updateRating(ratingId, newRatingValue);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( updatedRating );
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