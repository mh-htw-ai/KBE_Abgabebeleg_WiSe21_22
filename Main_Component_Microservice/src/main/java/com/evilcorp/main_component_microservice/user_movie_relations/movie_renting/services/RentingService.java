package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services;

import com.evilcorp.main_component_microservice.user.services.UserService;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.SimpleMovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.repositories.RentingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentingService {

    private final RentingRepository rentingRepository;
    private final UserService userService;
    private final DataWarehouseService dataWarehouseService;

    public MovieRenting getMovieRenting(UUID rentingId){
        return this.getMovieRentingByRepo(rentingId);
    }

    public List<MovieRenting> getAllRentings(){
        return rentingRepository.findAll();
    }

    public List<MovieRenting> getAllRentingsOfUser(UUID userId){
        userService.checkIfCorrespondingUserExists(userId);
        return rentingRepository.findAllByRenterIdIs(userId);
    }

    public UUID rentMovie(SimpleMovieRenting newRenting){
        UUID movieId = newRenting.getMovieId();
        UUID userId = newRenting.getRenterId();
        MovieRenting movieRenting = this.createMovieRenting(userId, movieId);
        return movieRenting.getId();
    }

    public MovieRenting updateRenting(UUID rentingId, Date newRentingStart){
        MovieRenting currentRenting = this.getMovieRentingByRepo(rentingId);
        currentRenting.setStartOfRenting(newRentingStart);
        rentingRepository.save(currentRenting);
        return currentRenting;
    }

    public void deleteRenting(UUID rentingId){
        MovieRenting rentingToBeDeleted = this.getMovieRentingByRepo(rentingId);
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
        userService.checkIfCorrespondingUserExists(userId);
        dataWarehouseService.checkIfCorrespondingMovieExists(movieId);
        MovieRenting movieRenting = new MovieRenting(movieId, userId);
        rentingRepository.save(movieRenting);
        return movieRenting;
    }
}
