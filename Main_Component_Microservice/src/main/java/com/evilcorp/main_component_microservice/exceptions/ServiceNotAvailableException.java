package com.evilcorp.main_component_microservice.exceptions;

public class ServiceNotAvailableException extends RuntimeException{
    public ServiceNotAvailableException(){
        super("Service is currently not available!");
    }
}
