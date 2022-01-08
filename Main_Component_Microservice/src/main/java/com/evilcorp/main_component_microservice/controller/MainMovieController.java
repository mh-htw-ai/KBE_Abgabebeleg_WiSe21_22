package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
import com.evilcorp.main_component_microservice.services.mwst_calculator_service.MwStService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/movies")
public class MainMovieController{

    private final DataWarehouseService dataWarehouseService;

    public MainMovieController( DataWarehouseService dataWarehouseService ) {
        this.dataWarehouseService = dataWarehouseService;
    }


    @GetMapping(value = "/{filmId}",
            produces = "application/json")
    public ResponseEntity<Film> getFilm(@PathVariable UUID filmId){

        return dataWarehouseService.getFilmById(filmId);
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


    @GetMapping(value = "/mwsttest")
    public ResponseEntity<Film> testMwstCalc(@RequestBody Film film){
        Film responseFilm = MwStService.calculateCostWithMwstFor(film);
        return ResponseEntity.ok(responseFilm);
    }
}
