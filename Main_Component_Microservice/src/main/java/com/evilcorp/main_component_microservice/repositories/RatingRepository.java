package com.evilcorp.main_component_microservice.repositories;

import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<MovieRating, UUID> {

    List<MovieRating> findAllByRatingDateAfter(Date ratingDate);

}
