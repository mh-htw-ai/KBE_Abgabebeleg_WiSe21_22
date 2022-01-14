package com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions;

import java.util.UUID;

public class MovieNotFoundException extends EntityNotFoundException{

    public MovieNotFoundException(UUID movieId) {
        super("Movie with id:" + movieId.toString() + " could not be found!");
    }

}
