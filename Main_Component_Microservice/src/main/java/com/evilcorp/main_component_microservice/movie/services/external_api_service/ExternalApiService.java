package com.evilcorp.main_component_microservice.movie.services.external_api_service;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public Movie translateMovieDescription(Movie movieObj){
        return movieObj;
    }

}
