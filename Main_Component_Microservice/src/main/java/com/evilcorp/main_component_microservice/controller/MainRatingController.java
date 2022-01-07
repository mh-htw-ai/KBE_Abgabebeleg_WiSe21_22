package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.RatingNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRatingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.DataWarehouseService;
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
@RequestMapping(MainRatingController.ratingURI)
public class MainRatingController extends AbstractMainController {

    final static String ratingURI = baseURI + "/ratings";

    private final RatingRepository ratingRepository;
    private final MovieRatingRepresentationAssembler ratingAssembler;
    private final DataWarehouseService dataWarehouseService;

    public MainRatingController(UserRepository userRepository,
                                UserRepresentationAssembler userAssembler,
                                RatingRepository ratingRepository,
                                MovieRatingRepresentationAssembler ratingAssembler,
                                MovieRepository movieRepository, DataWarehouseService dataWarehouseService) {
        super(userRepository,
                userAssembler,
                movieRepository);

        this.ratingRepository = ratingRepository;
        this.ratingAssembler = ratingAssembler;
        this.dataWarehouseService = dataWarehouseService;
    }


    @GetMapping(value = "/{ratingId}",
        produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRatingRepresentation> getMovieRating(@PathVariable UUID ratingId){
        return ratingRepository.findById(ratingId)
                .map(rating -> {
                    MovieRatingRepresentation ratingRepresentation = ratingAssembler.toModel(rating)
                            .add( linkTo( methodOn(MainRatingController.class).getAllMovieRatings() ).withRel("ratings") );
                    return ResponseEntity.status(HttpStatus.FOUND).body(ratingRepresentation);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<MovieRatingRepresentation>> getAllMovieRatings(){
        List<MovieRating> tempRatings = ratingRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body( ratingAssembler.toCollectionModel(tempRatings) );
    }



    @PostMapping(value = "/create",
            produces = "application/json")
    public ResponseEntity<Link> rateMovie(@RequestBody MovieRating newRating){
        User correspondingUser = userRepository.findById(newRating.getRatingOwner().getId())
                .orElseThrow(() -> new UserNotFoundException(newRating.getRatingOwner().getId()));

        movieRepository.findById(newRating.getMovieId())
                .orElseThrow(() -> new MovieNotFoundException(newRating.getMovieId()));

        if(dataWarehouseService.getFilm(newRating.getMovieId()).getStatusCode().isError()){
            throw new MovieNotFoundException(newRating.getMovieId());
        }

        ratingRepository.save(newRating);

        correspondingUser.ratingList.add(newRating);
        userRepository.save(correspondingUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkTo( methodOn(MainRatingController.class).getMovieRating( newRating.getId() ) ).withSelfRel() );
    }



    @PutMapping(value = "/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> updateRating(@RequestBody MovieRating newRating){
        MovieRating currentRating = ratingRepository.findById(newRating.getId())
                .orElseThrow(() -> new RatingNotFoundException(newRating.getId()));

        if(currentRating.getRatingOwner().equals(newRating.getRatingOwner())
                && currentRating.getMovieId().equals(newRating.getMovieId()) ){
            currentRating.setRating(newRating.getRating());
            currentRating.setRatingDate(newRating.getRatingDate());
        }

        ratingRepository.save(currentRating);
        return ResponseEntity.ok( linkTo( methodOn(MainRatingController.class).getMovieRating( currentRating.getId() ) ).withSelfRel() );
    }


    @DeleteMapping(value = "/delete/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable UUID ratingId){
        MovieRating tempRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RatingNotFoundException(ratingId));

        ratingRepository.delete(tempRating);

        return ResponseEntity.noContent().build();
    }

}
