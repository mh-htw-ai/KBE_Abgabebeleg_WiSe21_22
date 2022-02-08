package com.evilcorp.main_component_microservice.exceptions;

public class CsvCouldNotBeWrittenException extends RuntimeException{

    public CsvCouldNotBeWrittenException(){
        super("Csv could not be written to File!");
    }
}
