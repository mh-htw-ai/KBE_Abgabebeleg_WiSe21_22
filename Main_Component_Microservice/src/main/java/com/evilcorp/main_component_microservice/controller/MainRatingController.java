package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.RatingNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRatingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.model_representations.UserRepresentation;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
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

    final static String ratingURI = baseURI + "/rate";

    private final RatingRepository ratingRepository;
    private final MovieRatingRepresentationAssembler ratingAssembler;


    public MainRatingController(UserRepository userRepository,
                                UserRepresentationAssembler userAssembler,
                                RatingRepository ratingRepository,
                                MovieRatingRepresentationAssembler ratingAssembler,
                                MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                movieRepository);

        this.ratingRepository = ratingRepository;
        this.ratingAssembler = ratingAssembler;
    }


    @GetMapping(value = "/{ratingId}",
        produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<MovieRatingRepresentation> findMovieRating(@PathVariable UUID ratingId){
        return ratingRepository.findById(ratingId)
                .map(rating -> {
                    MovieRatingRepresentation ratingRepresentation = ratingAssembler.toModel(rating)
                            .add( linkTo( methodOn(MainRatingController.class).findAllMovieRatings() ).withRel("ratings") );
                    return ResponseEntity.status(HttpStatus.FOUND).body(ratingRepresentation);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<MovieRatingRepresentation>> findAllMovieRatings(){
        List<MovieRating> tempRatings = ratingRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body( ratingAssembler.toCollectionModel(tempRatings) );
    }



    @PutMapping(value = "/rateMovie/movie/{movieId}/user/{userId}/rating/{ratingValue}",
            produces = "application/json")
    public ResponseEntity<Link> rateMovie(@PathVariable UUID movieId, @PathVariable UUID userId, @PathVariable int ratingValue){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        MovieRating tempRating = new MovieRating(movieId, tempUser);
        tempRating.setRating(ratingValue);
        ratingRepository.save(tempRating);

        tempUser.ratingList.add(tempRating);
        userRepository.save(tempUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkTo( methodOn(MainRatingController.class).findMovieRating(tempRating.getId() ) ).withSelfRel() );
    }


    @PutMapping(value = "/change/{ratingId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> changeRating(@PathVariable UUID ratingId,@RequestBody MovieRating newRating){
        MovieRating tempRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RatingNotFoundException(ratingId));

        tempRating.setRating(newRating.getRating());
        ratingRepository.save(tempRating);

        return ResponseEntity.ok( linkTo( methodOn(MainRatingController.class).findMovieRating(tempRating.getId() ) ).withSelfRel() );
    }


    @DeleteMapping(value = "/delete/{ratingId}")
    public ResponseEntity deleteRating(@PathVariable UUID ratingId){
        MovieRating tempRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RatingNotFoundException(ratingId));
        ratingRepository.delete(tempRating);
        return ResponseEntity.noContent().build();
    }

}
