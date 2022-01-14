package com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions;

import java.util.UUID;

public abstract class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg){
        super(msg);
    }
}
