package com.evilcorp.main_component_microservice.exceptions.EntityAlreadyExistsExceptions;

import java.util.UUID;

public class UserAlreadyExistsException extends EntityAlreadyExistsException{
    public UserAlreadyExistsException(UUID userId) {
        super("User with same attributes already exists with Id: "+ userId.toString());
    }
}
