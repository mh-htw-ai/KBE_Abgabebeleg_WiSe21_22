package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.controllers;

import com.evilcorp.main_component_microservice.ParserService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.SimpleMovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services.RentingService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public ResponseEntity<MovieRentingRepresentation> getMovieRenting(@PathVariable String rentingIdString){
        UUID rentingId = parserService.parseStringToUUID(rentingIdString);
        MovieRentingRepresentation rentingRepresentation = rentingService.getMovieRenting(rentingId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(rentingRepresentation);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<CollectionModel<MovieRentingRepresentation>> getAllMovieRentings(){
        CollectionModel<MovieRentingRepresentation> rentingsRepresentation = rentingService.getAllRentings();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(rentingsRepresentation);
    }

    @GetMapping(value = "/of_user/{userIdString}",
            produces = "application/json")
    public ResponseEntity<CollectionModel<MovieRentingRepresentation>> getAllMovieRentingsOfUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        CollectionModel<MovieRentingRepresentation> rentingsRepresentation = rentingService.getAllRentingsOfUser(userId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(rentingsRepresentation);
    }

    @PostMapping(value = "/create",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> rentMovie(@RequestBody SimpleMovieRenting newRenting){
        Link linkToNewMovieRenting = rentingService.rentMovie(newRenting);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(linkToNewMovieRenting);
    }

    @PutMapping(value = "/update/{rentingIdString}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> updateRenting(@PathVariable String rentingIdString, @RequestBody Date newRentingDate){
        UUID rentingId = parserService.parseStringToUUID(rentingIdString);
        Link linkToNewMovieRenting = rentingService.updateRenting(rentingId, newRentingDate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(linkToNewMovieRenting);
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
