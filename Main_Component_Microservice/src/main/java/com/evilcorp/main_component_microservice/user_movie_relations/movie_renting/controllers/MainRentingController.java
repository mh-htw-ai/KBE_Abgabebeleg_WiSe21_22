package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.controllers;

import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.SimpleMovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services.RentingService;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping("/rentings")
public class MainRentingController{

    private final UserRepository userRepository;

    private final RentingRepository rentingRepository;
    private final MovieRentingRepresentationAssembler rentingAssembler;

    private final DataWarehouseService dataWarehouseService;

    private final RentingService rentingService;

    @GetMapping(value = "/{rentingId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRentingRepresentation> getMovieRenting(@PathVariable UUID rentingId){
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

    @GetMapping(value = "/of_user/{userId}",
            produces = "application/json")
    public ResponseEntity<CollectionModel<MovieRentingRepresentation>> getAllMovieRentingsOfUser(@PathVariable UUID userId){
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

    @PutMapping(value = "/update",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> updateRenting(@RequestBody MovieRenting newRenting){
        Link linkToNewMovieRenting = rentingService.updateRenting(newRenting);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(linkToNewMovieRenting);
    }

    @DeleteMapping(value = "/delete/{rentingId}")
    public ResponseEntity<?> deleteRenting(@PathVariable UUID rentingId){
        rentingService.deleteRenting(rentingId);
        return ResponseEntity
                .noContent()
                .build();
    }
}
