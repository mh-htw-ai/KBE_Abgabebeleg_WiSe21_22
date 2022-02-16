package com.evilcorp.main_component_microservice.user_movie_relations;

import com.evilcorp.main_component_microservice.exception_handling.EntityNotFoundException;

import java.util.UUID;

public class MovieNotFoundException extends EntityNotFoundException {
    public MovieNotFoundException(UUID movieId) {
        super("Movie with id:" + movieId.toString() + " could not be found!");
    }
}