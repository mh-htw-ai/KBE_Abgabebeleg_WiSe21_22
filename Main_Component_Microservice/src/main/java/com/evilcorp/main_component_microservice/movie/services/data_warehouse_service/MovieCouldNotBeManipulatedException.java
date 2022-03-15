package com.evilcorp.main_component_microservice.movie.services.data_warehouse_service;

public class MovieCouldNotBeManipulatedException extends RuntimeException {
    public MovieCouldNotBeManipulatedException(String failedAction){super("Movie could not be "+failedAction+"!");}
}
