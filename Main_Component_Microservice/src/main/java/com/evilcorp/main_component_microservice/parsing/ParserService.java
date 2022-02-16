package com.evilcorp.main_component_microservice.parsing;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
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