package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.RentingService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    @PostMapping(value = "/create",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> rentMovie(@RequestBody MovieRenting newRenting){
        Link linkToNewMovieRenting = rentingService.rentMovie(newRenting);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(linkToNewMovieRenting);
    }

    @PutMapping(value = "/update",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> updateRenting(@RequestBody MovieRenting newRenting){
        Link linkToNewMovieRenting = rentingService.rentMovie(newRenting);
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
