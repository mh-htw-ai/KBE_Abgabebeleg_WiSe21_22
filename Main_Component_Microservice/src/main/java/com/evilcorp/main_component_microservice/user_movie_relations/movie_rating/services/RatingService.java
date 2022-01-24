package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.services;

import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.RatingNotFoundException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations.MovieRatingRepresentationAssembler;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers.MainRatingController;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class RatingService {

    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;
    private final MovieRatingRepresentationAssembler ratingRepresentationAssembler;

    private final DataWarehouseService dataWarehouseService;


    public MovieRatingRepresentation getMovieRating(UUID ratingId){
        Optional<MovieRating> ratingContainer = ratingRepository.findById(ratingId);
        MovieRating rating;
        if(ratingContainer.isPresent()) {
            rating = ratingContainer.get();
        }else{
            throw new RatingNotFoundException(ratingId);
        }
        return ratingRepresentationAssembler.toModel(rating)
                .add( linkTo( methodOn(MainRatingController.class).getAllMovieRatings() ).withRel("ratings") );
    }

    public CollectionModel<MovieRatingRepresentation> getAllMovieRatings(){
        List<MovieRating> tempRatings = ratingRepository.findAll();
        return ratingRepresentationAssembler.toCollectionModel(tempRatings);
    }

    public Link rateMovie(MovieRating newMovieRating){
        UUID movieId = newMovieRating.getMovieId();
        UUID userId = newMovieRating.getRatingOwner().getId();

        User correspondingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Movie correspondingMovie = dataWarehouseService.getMovieById( movieId );
        if(correspondingMovie == null)throw new MovieNotFoundException( movieId );

        ratingRepository.save(newMovieRating);

        correspondingUser.addToRatings(newMovieRating);
        userRepository.save(correspondingUser);

        return linkTo( methodOn(MainRatingController.class).getMovieRating( newMovieRating.getId() ) ).withSelfRel();
    }

    public Link updateRating(MovieRating updateRating){
        MovieRating currentRating = ratingRepository.findById(updateRating.getId())
                .orElseThrow(() -> new RatingNotFoundException(updateRating.getId()));

        User currentRatingOwner = currentRating.getRatingOwner();
        User updateRatingOwner = updateRating.getRatingOwner();
        UUID currentRatingMovieId = currentRating.getMovieId();
        UUID updateRatingMovieId = updateRating.getMovieId();

        if(currentRatingOwner.equals(updateRatingOwner)
                && currentRatingMovieId.equals(updateRatingMovieId) ){
            currentRating.setRating(updateRating.getRating());
            currentRating.setRatingDate(updateRating.getRatingDate());
        }

        ratingRepository.save(currentRating);
        return linkTo( methodOn(MainRatingController.class).getMovieRating( currentRating.getId() ) ).withSelfRel();
    }

    public void deleteRating(UUID ratingId){
        MovieRating tempRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RatingNotFoundException(ratingId));

        ratingRepository.delete(tempRating);

        userRepository.findById(tempRating.getRatingOwner().getId())
                .orElseThrow(() -> new UserNotFoundException(tempRating.getRatingOwner().getId()));

        User correspondingUser = tempRating.getRatingOwner();
        correspondingUser.removeFromRatings(tempRating);
        userRepository.save(correspondingUser);
    }
}
