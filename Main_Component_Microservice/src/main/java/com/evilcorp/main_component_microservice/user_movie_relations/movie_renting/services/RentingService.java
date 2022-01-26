package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers.MainRatingController;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.RentingNotFoundException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class RentingService {
    private final UserRepository userRepository;

    private final RentingRepository rentingRepository;
    private final MovieRentingRepresentationAssembler rentingAssembler;

    private final DataWarehouseService dataWarehouseService;


    public MovieRentingRepresentation getMovieRenting(UUID rentingId){
        Optional<MovieRenting> rentingContainer = rentingRepository.findById(rentingId);
        MovieRenting renting;
        if(rentingContainer.isPresent()){
            renting = rentingContainer.get();
        }else{
            throw new RentingNotFoundException(rentingId);
        }
        return rentingAssembler.toModel(renting)
                .add( linkTo(methodOn(MainRentingController.class).getAllMovieRentings()).withRel("rentings"));
    }

    public CollectionModel<MovieRentingRepresentation> getAllRentings(){
        List<MovieRenting> rentings = rentingRepository.findAll();
        return rentingAssembler.toCollectionModel(rentings);
    }

    public CollectionModel<MovieRentingRepresentation> getAllRentingsOfUser(UUID userId){
        Optional<User> userContainer = userRepository.findById(userId);
        User supposedMovieRenter;
        if(userContainer.isPresent()){
            supposedMovieRenter = userContainer.get();
        }else{
            throw new UserNotFoundException(userId);
        }
        List<MovieRenting> rentingsOfUser = rentingRepository.findAllByMovieRenterIs(supposedMovieRenter);
        return rentingAssembler.toCollectionModel(rentingsOfUser);
    }

    public Link rentMovie(SimpleMovieRenting newRenting){
        UUID movieId = newRenting.getMovieId();
        UUID userId = newRenting.getRenterId();

        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Movie correspondingMovie = dataWarehouseService.getMovieById( movieId );
        if(correspondingMovie == null) throw new MovieNotFoundException( movieId );

        MovieRenting movieRenting = new MovieRenting(movieId, tempUser);

        rentingRepository.save(movieRenting);

        tempUser.addToRentings(movieRenting);
        userRepository.save(tempUser);

        return linkTo( methodOn(MainRentingController.class).getMovieRenting( newRenting.getMovieId() ) ).withSelfRel();
    }

    public Link updateRenting(MovieRenting updateRenting){
        UUID rentingId = updateRenting.getId();

        MovieRenting currentRenting = rentingRepository.findById(rentingId)
                .orElseThrow(() -> new RentingNotFoundException(rentingId));

        User currentRentingOwner = currentRenting.getMovieRenter();
        User updateRentingOwner = updateRenting.getMovieRenter();
        UUID currentRentingMovieId = currentRenting.getMovieId();
        UUID updateRentingMovieId = updateRenting.getMovieId();

        if(currentRentingOwner.equals(updateRentingOwner)
                && currentRentingMovieId.equals(updateRentingMovieId) ){
            currentRenting.setStartOfRenting(updateRenting.getStartOfRenting());
        }

        rentingRepository.save(currentRenting);
        return linkTo( methodOn(MainRatingController.class).getMovieRating( rentingId ) ).withSelfRel();
    }

    public void deleteRenting(UUID rentingId){
        MovieRenting tempRenting = rentingRepository.findById(rentingId)
                .orElseThrow(() -> new RentingNotFoundException(rentingId));

        UUID movieRenterId = tempRenting.getMovieRenter().getId();

        rentingRepository.delete(tempRenting);

        userRepository.findById(movieRenterId)
                .orElseThrow(() -> new UserNotFoundException(movieRenterId));

        User correspondingUser = tempRenting.getMovieRenter();
        correspondingUser.removeFromRentings(tempRenting);
        userRepository.save(correspondingUser);
    }
}
