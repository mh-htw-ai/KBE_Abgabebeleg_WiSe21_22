package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.services;

import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.RatingNotFoundException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.user.UserService;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.SimpleMovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations.MovieRatingRepresentationAssembler;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers.MainRatingController;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRatingRepresentationAssembler ratingRepresentationAssembler;
    private final UserService userService;
    private final DataWarehouseService dataWarehouseService;

    public MovieRatingRepresentation getMovieRating(UUID ratingId){
        MovieRating rating = this.getMovieRatingByRepo(ratingId);
        return ratingRepresentationAssembler
                .toModel(rating)
                .add( linkTo( methodOn(MainRatingController.class).getAllMovieRatings() ).withRel("ratings") );
    }

    public CollectionModel<MovieRatingRepresentation> getAllMovieRatings(){
        List<MovieRating> tempRatings = ratingRepository.findAll();
        return ratingRepresentationAssembler.toCollectionModel(tempRatings);
    }

    public CollectionModel<MovieRatingRepresentation> getAllMovieRatingsOfUserByRepo(UUID userId){
        User supposedRatingOwner = userService.getUserByRepo(userId);
        List<MovieRating> tempRatings = ratingRepository.findAllByRatingOwnerIs(supposedRatingOwner);
        return ratingRepresentationAssembler.toCollectionModel(tempRatings);
    }

    public Link rateMovie(SimpleMovieRating newSimpleMovieRating){
        UUID movieId = newSimpleMovieRating.getMovieId();
        UUID userId = newSimpleMovieRating.getOwnerId();
        int rating = newSimpleMovieRating.getRating();
        MovieRating newRating = this.createMovieRating(userId, movieId, rating);
        return linkTo( methodOn(MainRatingController.class).getMovieRating( newRating.getId() ) ).withSelfRel();
    }

    public Link updateRating(UUID ratingId, int newRatingValue){
        MovieRating currentRating = this.getMovieRatingByRepo(ratingId);
        currentRating.setRating(newRatingValue);
        ratingRepository.save(currentRating);
        return linkTo( methodOn(MainRatingController.class).getMovieRating( currentRating.getId() ) ).withSelfRel();
    }

    public void deleteRating(UUID ratingId){
        MovieRating ratingToBeDeleted = this.getMovieRatingByRepo(ratingId);
        User correspondingUser = ratingToBeDeleted.getRatingOwner();
        userService.deleteRatingFromUser(correspondingUser, ratingToBeDeleted);
        ratingRepository.delete(ratingToBeDeleted);
    }

    private MovieRating getMovieRatingByRepo(UUID ratingId){
        Optional<MovieRating> ratingContainer = ratingRepository.findById(ratingId);
        return this.unwrapRatingContainer(ratingContainer, ratingId);
    }

    private MovieRating unwrapRatingContainer(Optional<MovieRating> ratingContainer, UUID ratingId){
        MovieRating rating;
        if(ratingContainer.isPresent()) {
            rating = ratingContainer.get();
        }else{
            throw new RatingNotFoundException(ratingId);
        }
        return rating;
    }

    private MovieRating createMovieRating(UUID userId, UUID movieId, int rating){
        if(this.checkIfCorrespondingMovieExists(movieId)) {
            User correspondingUser = userService.getUserByRepo(userId);
            MovieRating movieRating = new MovieRating(movieId, correspondingUser, rating);
            userService.addNewRatingToUser(userId, movieRating);
            ratingRepository.save(movieRating);
            return movieRating;
        }
        throw new MovieNotFoundException(movieId);
    }

    private boolean checkIfCorrespondingMovieExists(UUID movieId){
        Movie correspondingMovie = dataWarehouseService.getMovieById( movieId );
        return correspondingMovie != null;
    }
}