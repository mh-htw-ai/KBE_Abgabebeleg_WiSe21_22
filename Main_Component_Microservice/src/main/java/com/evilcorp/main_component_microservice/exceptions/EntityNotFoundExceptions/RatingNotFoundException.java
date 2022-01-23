package com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions;

import java.util.UUID;

public class RatingNotFoundException extends EntityNotFoundException {
    public RatingNotFoundException(UUID ratingId) {super("Rating with id: " + ratingId.toString() + " could not be found!");}
}
