package com.evilcorp.main_component_microservice.custom_exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("User with id:" + userId.toString() + " could not be found!");
    }
}
