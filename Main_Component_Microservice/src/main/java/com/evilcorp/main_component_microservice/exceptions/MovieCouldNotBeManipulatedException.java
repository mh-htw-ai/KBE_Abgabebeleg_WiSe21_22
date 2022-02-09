package com.evilcorp.main_component_microservice.exceptions;

public class MovieCouldNotBeManipulatedException extends RuntimeException {
    public MovieCouldNotBeManipulatedException(String failedAction){super("Movie could not be "+failedAction+"!");}
}
