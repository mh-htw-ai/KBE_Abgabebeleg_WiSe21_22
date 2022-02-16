package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.repositories;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentingRepository extends JpaRepository<MovieRenting, UUID> {

    List<MovieRenting> findAllByMovieRenterIs(User movieRenter);

}
