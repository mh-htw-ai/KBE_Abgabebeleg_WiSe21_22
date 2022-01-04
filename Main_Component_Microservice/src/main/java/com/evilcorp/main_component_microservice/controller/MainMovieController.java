package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.Movie;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping(MainMovieController.movieURI)
public class MainMovieController extends AbstractMainController {

    final static String movieURI = baseURI + "/movies";
    final static String dataWarehouseURI = "http://localhost:21131/film";

    public MainMovieController(UserRepository userRepository,
                               UserRepresentationAssembler userAssembler,
                               MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                movieRepository);
    }


    //TODO: weiterreichen der anfragen an den Data Warehouse Microservice
    @GetMapping(value = "/{movieId}",
            produces = "application/json")
    public ResponseEntity<Movie> getMovie(@PathVariable UUID movieId){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Movie> responseEntity =
                restTemplate
                        .getForEntity(
                                dataWarehouseURI+"/{movieId}",
                                Movie.class,
                                movieId
                        );

        return responseEntity;
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<Movie>> getAllMovies(){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CollectionModel<Movie>> responseEntity =
                restTemplate
                        .exchange(
                                dataWarehouseURI,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<CollectionModel<Movie>>() {}
                        );

        return responseEntity;
    }



    @PostMapping(value = "/create",
            consumes = "application/json")
    public ResponseEntity creatMovie(@RequestBody Movie newMovie){

        RestTemplate restTemplate = new RestTemplate();
       // ResponseEntity<Link> responseEntity = restTemplate.postForEntity(dataWarehouseURI+"/create", Link.class);

        return ResponseEntity.ok().build();
    }



    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity updateMovie(@RequestBody Movie changedMovie){


        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "/delete/{movieId}")
    public ResponseEntity deleteMovie(@PathVariable UUID movieId){

        return ResponseEntity.ok().build();
    }


}
