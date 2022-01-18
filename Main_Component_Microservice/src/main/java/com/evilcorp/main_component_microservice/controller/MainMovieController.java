package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
import com.evilcorp.main_component_microservice.services.mwst_calculator_service.MwStService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Validated
@RequestMapping("/movies")
public class MainMovieController{

    private final DataWarehouseService dataWarehouseService;

    public MainMovieController( DataWarehouseService dataWarehouseService ) {
        this.dataWarehouseService = dataWarehouseService;
    }


    @GetMapping(value = "/{filmId}",
            produces = "application/json")
    public ResponseEntity getFilm(@PathVariable UUID filmId){

        return dataWarehouseService.getFilmById(filmId);
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<Film[]> getAllMovies(){

        return dataWarehouseService.getAllFilms();
    }



    @PostMapping(value = "/create",
            consumes = "application/json")
    public ResponseEntity createMovie(@RequestBody List<@Valid Film> newFilm){
        return dataWarehouseService.createFilm(newFilm);
    }



    @PutMapping(value = "/update",
            consumes = "application/json")
    public ResponseEntity updateMovie(@Valid @RequestBody Film changedFilm){

        ResponseEntity dataWarehouseResponse = dataWarehouseService.changeMovie(changedFilm);
        if(dataWarehouseResponse.getStatusCode().equals(HttpStatus.NOT_FOUND)) return dataWarehouseResponse;

        return ResponseEntity.ok( linkTo( methodOn(MainMovieController.class).getFilm( changedFilm.getId() ) ).withSelfRel() );
    }


    @DeleteMapping(value = "/delete/{filmId}")
    public ResponseEntity deleteMovie(@PathVariable UUID filmId){

        return dataWarehouseService.deleteMovie(filmId);
    }


    @GetMapping(value = "/mwsttest")
    public ResponseEntity<Film> testMwstCalc(@RequestBody Film film){
        Film responseFilm = MwStService.calculateCostWithMwstFor(film);
        return ResponseEntity.ok(responseFilm);
    }
}
