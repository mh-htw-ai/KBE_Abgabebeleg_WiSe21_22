package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class AbstractMainController {

    protected final static String baseURI = "/main/api/v1.0";

    //TODO: Anpassen der Request attribute
    //TODO: error handling wenn user bereits existiert und erneut erstellt werden soll
    //TODO: error klassen vererbung ueberdenken
    //TODO: service klassen bearebeiten (mwst, datawarehouse)
    //TODO: controllervererbung ueberdenken gibt es besser möglichkeit gemeinsamen grundpfad zu definieren
    //TODO: logging verschöneren sowohl consolen ausgabe als auch loggen der methoden aufrufe

    protected final UserRepository userRepository;
    protected final UserRepresentationAssembler userAssembler;

    protected final MovieRepository movieRepository;
}
