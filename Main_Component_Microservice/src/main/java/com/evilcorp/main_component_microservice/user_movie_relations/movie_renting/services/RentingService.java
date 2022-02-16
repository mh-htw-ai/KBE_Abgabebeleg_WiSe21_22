package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.user.services.UserService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers.MainRatingController;
import com.evilcorp.main_component_microservice.user_movie_relations.MovieNotFoundException;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.SimpleMovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.controllers.MainRentingController;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.repositories.RentingRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class RentingService {

    private final RentingRepository rentingRepository;
    private final MovieRentingRepresentationAssembler rentingAssembler;
    private final UserService userService;
    private final DataWarehouseService dataWarehouseService;

    public MovieRentingRepresentation getMovieRenting(UUID rentingId){
        MovieRenting renting = this.getMovieRentingByRepo(rentingId);
        return rentingAssembler
                .toModel(renting)
                .add( linkTo(methodOn(MainRentingController.class).getAllMovieRentings()).withRel("rentings"));
    }

    public CollectionModel<MovieRentingRepresentation> getAllRentings(){
        List<MovieRenting> rentings = rentingRepository.findAll();
        return rentingAssembler.toCollectionModel(rentings);
    }

    public CollectionModel<MovieRentingRepresentation> getAllRentingsOfUser(UUID userId){
        User supposedMovieRenter = userService.getUserByRepo(userId);
        List<MovieRenting> rentingsOfUser = rentingRepository.findAllByMovieRenterIs(supposedMovieRenter);
        return rentingAssembler.toCollectionModel(rentingsOfUser);
    }

    public Link rentMovie(SimpleMovieRenting newRenting){
        UUID movieId = newRenting.getMovieId();
        UUID userId = newRenting.getRenterId();
        MovieRenting movieRenting = this.createMovieRenting(userId, movieId);
        return linkTo( methodOn(MainRentingController.class).getMovieRenting( movieRenting.getId().toString() ) ).withSelfRel();
    }

    public Link updateRenting(UUID rentingId, Date newRentingStart){
        MovieRenting currentRenting = this.getMovieRentingByRepo(rentingId);
        currentRenting.setStartOfRenting(newRentingStart);
        rentingRepository.save(currentRenting);
        return linkTo( methodOn(MainRatingController.class).getMovieRating( rentingId.toString() ) ).withSelfRel();
    }

    public void deleteRenting(UUID rentingId){
        MovieRenting rentingToBeDeleted = this.getMovieRentingByRepo(rentingId);
        User correspondingUser = rentingToBeDeleted.getMovieRenter();
        userService.deleteRentingFromUser(correspondingUser, rentingToBeDeleted);
        rentingRepository.delete(rentingToBeDeleted);
    }

    private MovieRenting getMovieRentingByRepo(UUID rentingId){
        Optional<MovieRenting> rentingContainer = rentingRepository.findById(rentingId);
        return this.unwrapRentingContainer(rentingContainer, rentingId);
    }

    private MovieRenting unwrapRentingContainer(Optional<MovieRenting> rentingContainer, UUID rentingId){
        MovieRenting renting;
        if(rentingContainer.isPresent()) {
            renting = rentingContainer.get();
        }else{
            throw new RentingNotFoundException(rentingId);
        }
        return renting;
    }

    private MovieRenting createMovieRenting(UUID userId, UUID movieId){
        if(this.checkIfCorrespondingMovieExists(movieId)) {
            User correspondingUser = userService.getUserByRepo(userId);
            MovieRenting movieRenting = new MovieRenting(movieId, correspondingUser);
            userService.addNewRentingToUser(userId, movieRenting);
            rentingRepository.save(movieRenting);
            return movieRenting;
        }
        throw new MovieNotFoundException(movieId);
    }

    private boolean checkIfCorrespondingMovieExists(UUID movieId){
        Movie correspondingMovie = dataWarehouseService.getMovieById( movieId );
        return correspondingMovie != null;
    }
}
