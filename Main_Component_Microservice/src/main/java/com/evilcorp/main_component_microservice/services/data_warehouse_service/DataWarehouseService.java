package com.evilcorp.main_component_microservice.services.data_warehouse_service;

import com.evilcorp.main_component_microservice.services.mwst_calculator_service.MwStService;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.UUID;

@Service
@NoArgsConstructor
public class DataWarehouseService {

    private final static String dataWarehouseURI = "http://localhost:21131/film";
    private final static RestTemplate restTemplate = new RestTemplate();


    public ResponseEntity<Film> getFilmById(UUID movieId){
        ResponseEntity<Film> responseEntity = restTemplate.getForEntity(dataWarehouseURI + movieId.toString(), Film.class);

        Film datawarehouseResponseFilm = responseEntity.getBody();
        assert datawarehouseResponseFilm != null;
        Film mwstResponseFilm = MwStService.calculateCostWithMwstFor(datawarehouseResponseFilm);

        return ResponseEntity.status(HttpStatus.FOUND).body(mwstResponseFilm);
    }


    public ResponseEntity<Film[]> getAllFilms(){
        ResponseEntity<Film[]> allFilms = restTemplate.getForEntity(dataWarehouseURI, Film[].class);

        Film[] datawarehouseResponseFilms = allFilms.getBody();
        assert datawarehouseResponseFilms != null;
        Film[] mwstResponseFilms = MwStService.calculateCostWithMwstForMultipleFilms(datawarehouseResponseFilms);

        return ResponseEntity.status(HttpStatus.FOUND).body(mwstResponseFilms);
    }


    public void createFilm(Film newFilm){
        URI uri = restTemplate.postForLocation(dataWarehouseURI , newFilm);

    }


    public void changeMovie(Film changedFilm){
        URI locationURI = restTemplate.postForLocation(dataWarehouseURI , changedFilm);
    }


    public void deleteMovie(UUID filmId){
        restTemplate.delete(dataWarehouseURI + filmId.toString());
    }
}
