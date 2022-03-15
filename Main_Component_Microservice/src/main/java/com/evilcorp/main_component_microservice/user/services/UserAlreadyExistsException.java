package com.evilcorp.main_component_microservice.user.services;

import com.evilcorp.main_component_microservice.exception_handling.EntityAlreadyExistsException;

import java.util.UUID;

public class UserAlreadyExistsException extends EntityAlreadyExistsException {
    public UserAlreadyExistsException(UUID userId) {
        super("User with same attributes already exists with Id: "+ userId.toString());
    }
}
