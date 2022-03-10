package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<MovieRating, UUID> {

    List<MovieRating> findAllByOwnerIdIs(UUID ownerId);

    List<MovieRating> findAllByRatingDateAfter(Date ratingDate);
}
