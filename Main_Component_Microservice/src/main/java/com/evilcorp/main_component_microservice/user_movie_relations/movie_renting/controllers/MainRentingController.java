package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.controllers;

import com.evilcorp.main_component_microservice.parsing.ParserService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.SimpleMovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services.RentingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/rentings")
public class MainRentingController{

    private final ParserService parserService;
    private final RentingService rentingService;

    @GetMapping(value = "/{rentingIdString}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRenting> getMovieRenting(@PathVariable String rentingIdString){
        UUID rentingId = parserService.parseStringToUUID(rentingIdString);
        MovieRenting renting = rentingService.getMovieRenting(rentingId);
        return ResponseEntity
                .status( HttpStatus.FOUND )
                .body( renting );
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<MovieRenting>> getAllMovieRentings(){
        List<MovieRenting> rentings = rentingService.getAllRentings();
        return ResponseEntity
                .status( HttpStatus.FOUND )
                .body( rentings );
    }

    @GetMapping(value = "/of_user/{userIdString}",
            produces = "application/json")
    public ResponseEntity<List<MovieRenting>> getAllMovieRentingsOfUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        List<MovieRenting> rentings = rentingService.getAllRentingsOfUser(userId);
        return ResponseEntity
                .status( HttpStatus.FOUND )
                .body( rentings );
    }

    @PostMapping(value = "/create",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> rentMovie(@RequestBody SimpleMovieRenting newRenting){
        UUID rentingId = rentingService.rentMovie(newRenting);
        return ResponseEntity
                .status( HttpStatus.CREATED )
                .body( rentingId );
    }

    @PutMapping(value = "/update/{rentingIdString}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovieRenting> updateRenting(@PathVariable String rentingIdString, @RequestBody Date newRentingDate){
        UUID rentingId = parserService.parseStringToUUID(rentingIdString);
        MovieRenting updatedRenting = rentingService.updateRenting(rentingId, newRentingDate);
        return ResponseEntity
                .status( HttpStatus.CREATED )
                .body( updatedRenting );
    }

    @DeleteMapping(value = "/delete/{rentingIdString}")
    public ResponseEntity<?> deleteRenting(@PathVariable String rentingIdString){
        UUID rentingId = parserService.parseStringToUUID(rentingIdString);
        rentingService.deleteRenting(rentingId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
