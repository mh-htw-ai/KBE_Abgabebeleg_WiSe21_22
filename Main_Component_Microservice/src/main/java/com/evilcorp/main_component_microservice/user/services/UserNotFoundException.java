package com.evilcorp.main_component_microservice.user.services;

import com.evilcorp.main_component_microservice.exception_handling.EntityNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(UUID userId) {
        super("User with id:" + userId.toString() + " could not be found!");
    }
}
