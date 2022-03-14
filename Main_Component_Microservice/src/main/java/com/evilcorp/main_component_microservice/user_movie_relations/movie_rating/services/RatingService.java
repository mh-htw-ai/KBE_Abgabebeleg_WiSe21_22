package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.services;

import com.evilcorp.main_component_microservice.user.services.UserService;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.SimpleMovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final DataWarehouseService dataWarehouseService;

    public MovieRating getMovieRating(UUID ratingId){
        return this.getMovieRatingByRepo(ratingId);
    }

    public List<MovieRating> getAllMovieRatings(){
        return ratingRepository.findAll();
    }

    public List<MovieRating> getAllMovieRatingsOfUserByRepo(UUID userId){
        userService.checkIfCorrespondingUserExists(userId);
        return ratingRepository.findAllByOwnerId(userId);
    }

    public UUID rateMovie(SimpleMovieRating newSimpleMovieRating){
        UUID movieId = newSimpleMovieRating.getMovieId();
        UUID userId = newSimpleMovieRating.getOwnerId();
        int rating = newSimpleMovieRating.getRating();
        MovieRating newRating = this.createMovieRating(userId, movieId, rating);
        return newRating.getId();
    }

    public MovieRating updateRating(UUID ratingId, int newRatingValue){
        MovieRating currentRating = this.getMovieRatingByRepo(ratingId);
        currentRating.setRating(newRatingValue);
        ratingRepository.save(currentRating);
        return currentRating;
    }

    public void deleteRating(UUID ratingId){
        MovieRating ratingToBeDeleted = this.getMovieRatingByRepo(ratingId);
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
        userService.checkIfCorrespondingUserExists(userId);
        dataWarehouseService.checkIfCorrespondingMovieExists(movieId);
        MovieRating movieRating = new MovieRating(movieId, userId, rating);
        ratingRepository.save(movieRating);
        return movieRating;
    }
}