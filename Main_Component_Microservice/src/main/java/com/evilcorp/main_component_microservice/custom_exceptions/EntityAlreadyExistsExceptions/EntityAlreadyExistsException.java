package com.evilcorp.main_component_microservice.custom_exceptions.EntityAlreadyExistsExceptions;

public class EntityAlreadyExistsException extends RuntimeException{

    public EntityAlreadyExistsException(String msg){
        super(msg);
    }
}
