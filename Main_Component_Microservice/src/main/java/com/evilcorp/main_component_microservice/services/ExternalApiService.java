package com.evilcorp.main_component_microservice.services;

import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public Film translateMovieDescription(Film filmObj){
        return filmObj;
    }

}
