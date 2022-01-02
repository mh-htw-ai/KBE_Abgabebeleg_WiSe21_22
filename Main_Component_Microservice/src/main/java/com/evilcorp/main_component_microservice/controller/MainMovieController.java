package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.Movie;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MainMovieController.movieURI)
public class MainMovieController extends AbstractMainController {

    final static String movieURI = baseURI + "/movie";

    public MainMovieController(UserRepository userRepository,
                               UserRepresentationAssembler userAssembler,
                               MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                movieRepository);
    }


    //TODO: weiterreichen der anfragen an den Data Warehouse Microservice

    public ResponseEntity<Movie> getMovie(){

        return ResponseEntity.ok().build();
    }



    public ResponseEntity<CollectionModel<Movie>> getAllMovies(){


        return ResponseEntity.ok().build();
    }



    public ResponseEntity creatMovie(){


        return ResponseEntity.ok().build();
    }



    public ResponseEntity updateMovie(){


        return ResponseEntity.ok().build();
    }



    public ResponseEntity deleteMovie(){

        return ResponseEntity.ok().build();
    }


}
