package com.evilcorp.main_component_microservice.exception_handling;

public abstract class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg){
        super(msg);
    }
}
