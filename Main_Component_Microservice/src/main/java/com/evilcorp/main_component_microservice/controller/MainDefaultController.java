package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.RatingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.RentingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class MainDefaultController {

    protected final static String baseURI = "/main/api/v1.0/";

    protected final UserRepository userRepository;
    protected final UserModelAssembler userAssembler;

    protected final RatingRepository ratingRepository;
    protected final RatingModelAssembler ratingAssembler;

    protected final RentingRepository rentingRepository;
    protected final RentingModelAssembler rentingAssembler;

    protected final MovieRepository movieRepository;
}
