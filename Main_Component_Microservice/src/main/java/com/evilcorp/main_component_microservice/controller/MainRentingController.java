package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
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
@RequestMapping("/rentings")
public class MainRentingController{

    private final UserRepository userRepository;

    private final RentingRepository rentingRepository;
    private final MovieRentingRepresentationAssembler rentingAssembler;

    private final DataWarehouseService dataWarehouseService;


    public MainRentingController(UserRepository userRepository,
                                 RentingRepository rentingRepository,
                                 MovieRentingRepresentationAssembler rentingAssembler,
                                 DataWarehouseService dataWarehouseService) {

        this.userRepository = userRepository;
        this.rentingRepository = rentingRepository;
        this.rentingAssembler = rentingAssembler;
        this.dataWarehouseService = dataWarehouseService;
    }




    @GetMapping(value = "/{rentingId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRentingRepresentation> getMovieRenting(@PathVariable UUID rentingId){
        return rentingRepository.findById(rentingId)
                .map(renting -> {
                    MovieRentingRepresentation rentingRepresentation = rentingAssembler.toModel(renting)
                            .add( linkTo( methodOn(MainRentingController.class).getAllMovieRentings() ).withRel("rentings") );
                    return ResponseEntity.ok(rentingRepresentation);
                })
                .orElse(ResponseEntity.notFound().build());
    }




    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<CollectionModel<MovieRentingRepresentation>> getAllMovieRentings(){
        List<MovieRenting> tempRentings = rentingRepository.findAll();
        return ResponseEntity.ok( rentingAssembler.toCollectionModel(tempRentings) );
    }




    @PostMapping(value = "/rentMovie",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> rentMovie(@RequestBody MovieRenting newRenting){
        UUID userId = newRenting.getMovieRenter().getId();
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if(dataWarehouseService.getFilmById( newRenting.getMovieID() ).getStatusCode().isError()){
            throw new MovieNotFoundException(newRenting.getMovieID());
        }

        rentingRepository.save(newRenting);

        tempUser.addToRentings(newRenting);
        userRepository.save(tempUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkTo( methodOn(MainRentingController.class).getMovieRenting( newRenting.getId() ) ).withSelfRel() );
    }




}
