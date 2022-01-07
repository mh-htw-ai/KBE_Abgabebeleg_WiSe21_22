package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(MainRentingController.rentingURI)
public class MainRentingController extends AbstractMainController {

    final static String rentingURI = baseURI + "/rent";

    private final RentingRepository rentingRepository;
    private final MovieRentingRepresentationAssembler rentingAssembler;


    public MainRentingController(UserRepository userRepository,
                                 UserRepresentationAssembler userAssembler,
                                 RentingRepository rentingRepository,
                                 MovieRentingRepresentationAssembler rentingAssembler,
                                 MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                movieRepository);

        this.rentingRepository = rentingRepository;
        this.rentingAssembler = rentingAssembler;
    }




    @GetMapping(value = "/{rentingId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRentingRepresentation> findMovieRenting(@PathVariable UUID rentingId){
        return rentingRepository.findById(rentingId)
                .map(renting -> {
                    MovieRentingRepresentation rentingRepresentation = rentingAssembler.toModel(renting)
                            .add( linkTo( methodOn(MainRentingController.class).findAllMovieRentings() ).withRel("rentings") );
                    return ResponseEntity.ok(rentingRepresentation);
                })
                .orElse(ResponseEntity.notFound().build());
    }




    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<CollectionModel<MovieRentingRepresentation>> findAllMovieRentings(){
        List<MovieRenting> tempRentings = rentingRepository.findAll();
        return ResponseEntity.ok( rentingAssembler.toCollectionModel(tempRentings) );
    }




    @PutMapping(value = "/rentMovie/movie/{movieId}/user/{userId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String rentMovie(@PathVariable UUID movieId, @PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        MovieRenting tempRenting = new MovieRenting(movieId, tempUser);
        rentingRepository.save(tempRenting);

        tempUser.rentingList.add(tempRenting);
        userRepository.save(tempUser);

        return "Done";//rentingAssembler.toModel(tempRenting);
    }

}
