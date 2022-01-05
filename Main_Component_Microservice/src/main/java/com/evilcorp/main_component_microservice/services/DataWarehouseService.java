package com.evilcorp.main_component_microservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.UUID;

@Service
public class DataWarehouseService {

    //TODO: gegebenenfalls anpassen an datawarehouse
    //TODO: discorvering the URI

    private final static String dataWarehouseURI = "http://localhost:21131/film";
    private final static RestTemplate restTemplate = new RestTemplate();

    public DataWarehouseService() {
    }

    public ResponseEntity<Film> getFilm(UUID movieId){
        ResponseEntity<Film> tempFilmResponse = restTemplate.getForEntity(dataWarehouseURI+ movieId.toString(), Film.class);
        return tempFilmResponse;
    }

    public ResponseEntity<Film[]> getAllFilms(){
        ResponseEntity<Film[]> tempMovie = restTemplate.getForEntity(dataWarehouseURI, Film[].class);

        return tempMovie;
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
