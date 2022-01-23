package com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions;

public abstract class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg){
        super(msg);
    }
}
