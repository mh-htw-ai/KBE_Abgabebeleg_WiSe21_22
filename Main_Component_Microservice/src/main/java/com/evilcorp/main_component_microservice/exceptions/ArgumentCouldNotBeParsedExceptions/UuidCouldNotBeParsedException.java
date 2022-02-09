package com.evilcorp.main_component_microservice.exceptions.ArgumentCouldNotBeParsedExceptions;

public class UuidCouldNotBeParsedException extends RuntimeException{
    public UuidCouldNotBeParsedException(String idString){ super("The Id string: "+idString+" could not be parsed to an UUID-object!");}
}
