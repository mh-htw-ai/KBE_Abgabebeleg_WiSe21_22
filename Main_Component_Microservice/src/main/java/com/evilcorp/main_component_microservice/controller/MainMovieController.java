package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.Movie;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.DataWarehouseService;
import com.evilcorp.main_component_microservice.services.Film;
import org.jboss.jandex.Main;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(MainMovieController.movieURI)
public class MainMovieController extends AbstractMainController {

    final static String movieURI = baseURI + "/movies";

    private final DataWarehouseService dataWarehouseService;

    public MainMovieController(UserRepository userRepository,
                               UserRepresentationAssembler userAssembler,
                               MovieRepository movieRepository,
                               DataWarehouseService dataWarehouseService) {
        super(userRepository,
                userAssembler,
                movieRepository);

        this.dataWarehouseService = dataWarehouseService;
    }


    @GetMapping(value = "/{filmId}",
            produces = "application/json")
    public ResponseEntity<Film> getFilm(@PathVariable UUID filmId){

        return dataWarehouseService.getFilm(filmId);
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity getAllMovies(){

        return dataWarehouseService.getAllFilms();
    }



    @PostMapping(value = "/create",
            consumes = "application/json")
    public ResponseEntity createMovie(@RequestBody Film newFilm){

        dataWarehouseService.createFilm(newFilm);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkTo( methodOn(MainMovieController.class).getFilm( newFilm.getId()) ).withSelfRel() );
    }



    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity updateMovie(@RequestBody Film changedFilm){

        dataWarehouseService.changeMovie(changedFilm);

        return ResponseEntity.ok( linkTo( methodOn(MainMovieController.class).getFilm( changedFilm.getId() ) ).withSelfRel() );
    }


    @DeleteMapping(value = "/delete/{filmId}")
    public ResponseEntity deleteMovie(@PathVariable UUID filmId){

        dataWarehouseService.deleteMovie(filmId);

        return ResponseEntity.noContent().build();
    }


}
