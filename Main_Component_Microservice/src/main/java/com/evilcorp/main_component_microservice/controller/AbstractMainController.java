package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class AbstractMainController {

    protected final static String baseURI = "/main/api/v1.0";

    protected final UserRepository userRepository;
    protected final UserRepresentationAssembler userAssembler;

    protected final MovieRepository movieRepository;
}
