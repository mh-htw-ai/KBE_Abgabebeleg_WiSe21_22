package com.evilcorp.main_component_microservice.custom_exceptions;

public class ServiceNotAvailableException extends RuntimeException{
    public ServiceNotAvailableException(){
        super("Service is currently not available!");
    }
}
