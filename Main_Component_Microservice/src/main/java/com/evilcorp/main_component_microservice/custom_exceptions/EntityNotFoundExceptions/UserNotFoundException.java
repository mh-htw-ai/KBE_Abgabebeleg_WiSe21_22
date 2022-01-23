package com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions;

import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(UUID userId) {
        super("User with id:" + userId.toString() + " could not be found!");
    }
}
