package com.evilcorp.main_component_microservice;

import com.evilcorp.main_component_microservice.exceptions.ArgumentCouldNotBeParsedExceptions.UuidCouldNotBeParsedException;

import java.util.UUID;

public class ParserService {
    public UUID parseStringToUUID(String idString){
        UUID idObj;
        try{
            idObj = UUID.fromString(idString);
        }catch (IllegalArgumentException e){
            throw new UuidCouldNotBeParsedException(idString);
        }
        return idObj;
    }
}