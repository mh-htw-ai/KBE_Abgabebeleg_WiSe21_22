package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.RatingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.RentingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MainMovieController.movieURI)
public class MainMovieController extends MainDefaultController {

    final static String movieURI = baseURI + "/movie";

    public MainMovieController(UserRepository userRepository,
                               UserModelAssembler userAssembler,
                               RatingRepository ratingRepository,
                               RatingModelAssembler ratingAssembler,
                               RentingRepository rentingRepository,
                               RentingModelAssembler rentingAssembler,
                               MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                ratingRepository,
                ratingAssembler,
                rentingRepository,
                rentingAssembler,
                movieRepository);
    }


    //TODO: weiterreichen der anfragen an den Data Warehouse Microservice

    public void getMovie(){

    }

    public void getAllMovies(){

    }

    public void creatMovie(){

    }

    public void updateMovie(){

    }

    public void deleteMovie(){

    }
}
