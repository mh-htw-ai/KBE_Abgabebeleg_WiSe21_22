package com.evilcorp.main_component_microservice.movie.services.csv_exporter_service;

public class CsvCouldNotBeWrittenException extends RuntimeException{

    public CsvCouldNotBeWrittenException(){
        super("Csv could not be written to File!");
    }
}
