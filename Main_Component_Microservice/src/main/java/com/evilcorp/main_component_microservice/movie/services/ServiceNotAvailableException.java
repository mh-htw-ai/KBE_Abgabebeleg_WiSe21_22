package com.evilcorp.main_component_microservice.movie.services;

public class ServiceNotAvailableException extends RuntimeException{
    public ServiceNotAvailableException(){
        super("Service is currently not available!");
    }
}
