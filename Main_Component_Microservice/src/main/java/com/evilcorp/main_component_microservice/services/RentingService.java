package com.evilcorp.main_component_microservice.services;

import com.evilcorp.main_component_microservice.controller.MainRatingController;
import com.evilcorp.main_component_microservice.controller.MainRentingController;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.MovieNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.RatingNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.RentingNotFoundException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.MovieRentingRepresentation;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
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

    public Link rentMovie(MovieRenting newRenting){
        UUID movieId = newRenting.getMovieId();
        UUID userId = newRenting.getMovieRenter().getId();

        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Film correspondingMovie = dataWarehouseService.getMovieById( movieId );
        if(correspondingMovie == null) throw new MovieNotFoundException( movieId );

        rentingRepository.save(newRenting);

        tempUser.addToRentings(newRenting);
        userRepository.save(tempUser);

        return linkTo( methodOn(MainRentingController.class).getMovieRenting( newRenting.getId() ) ).withSelfRel();
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
