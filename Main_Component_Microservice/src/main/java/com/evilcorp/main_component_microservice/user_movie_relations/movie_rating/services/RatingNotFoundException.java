package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.services;

import com.evilcorp.main_component_microservice.exception_handling.EntityNotFoundException;

import java.util.UUID;

public class RatingNotFoundException extends EntityNotFoundException {
    public RatingNotFoundException(UUID ratingId) {super("Rating with id: " + ratingId.toString() + " could not be found!");}
}
