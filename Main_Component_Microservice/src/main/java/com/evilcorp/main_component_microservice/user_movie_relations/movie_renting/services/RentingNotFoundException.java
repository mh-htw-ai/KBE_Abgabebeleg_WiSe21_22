package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.services;

import com.evilcorp.main_component_microservice.exception_handling.EntityNotFoundException;

import java.util.UUID;

public class RentingNotFoundException extends EntityNotFoundException {
    public RentingNotFoundException(UUID rentingId) {super("Renting with id: " + rentingId.toString() + " could not be found!");}
}
