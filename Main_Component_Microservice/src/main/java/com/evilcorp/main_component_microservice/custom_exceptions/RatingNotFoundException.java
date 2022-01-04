package com.evilcorp.main_component_microservice.custom_exceptions;

import java.util.UUID;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(UUID ratingId) {super("Rating with id: " + ratingId.toString() + " could not be found!");}
}
