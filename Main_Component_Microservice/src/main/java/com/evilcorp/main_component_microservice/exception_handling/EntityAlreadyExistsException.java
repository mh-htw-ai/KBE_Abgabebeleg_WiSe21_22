package com.evilcorp.main_component_microservice.exception_handling;

public abstract class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String msg){
        super(msg);
    }
}
