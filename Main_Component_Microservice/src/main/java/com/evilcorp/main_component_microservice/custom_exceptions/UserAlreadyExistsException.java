package com.evilcorp.main_component_microservice.custom_exceptions;

import java.util.UUID;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(UUID userId) {
        super("User with same attributes already exists with Id: "+ userId.toString());
    }
}
