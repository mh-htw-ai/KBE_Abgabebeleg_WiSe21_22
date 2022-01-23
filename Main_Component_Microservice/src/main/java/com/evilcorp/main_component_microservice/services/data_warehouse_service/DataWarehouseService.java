package com.evilcorp.main_component_microservice.services.data_warehouse_service;

import com.evilcorp.main_component_microservice.custom_exceptions.ServiceNotAvailableException;
import com.evilcorp.main_component_microservice.services.mwst_calculator_service.MwStService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@Slf4j
public class DataWarehouseService {

    private final static String dataWarehouseURI = "http://localhost:21131/film";
    private final static RestTemplate restTemplate = new RestTemplate();
    private final MwStService mwStService = new MwStService();

    public Film getMovieById(UUID movieId){

        HttpEntity<String> requestEntity = this.setupHttpEntityDefaultStringBody();

        ResponseEntity<Film> responseEntity;
        try {
            responseEntity = restTemplate.exchange(dataWarehouseURI + "/uuid=" + movieId.toString(), HttpMethod.GET, requestEntity, Film.class);
        }catch (HttpClientErrorException | ResourceAccessException e){
            log.error("Datawarehouse-Microservice could not be reached!");
            return null;
        }
        Film datawarehouseResponseMovie = responseEntity.getBody();
        assert datawarehouseResponseMovie != null;

        return datawarehouseResponseMovie;
    }

    public List<Film> getAllMovies(){

        HttpEntity<String> requestEntity = this.setupHttpEntityDefaultStringBody();

        ResponseEntity<Film[]> allMovie;
        try {
            allMovie = restTemplate.exchange(dataWarehouseURI + "/all", HttpMethod.GET, requestEntity, Film[].class);
        }catch(HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }

        Film[] datawarehouseResponseMovies = allMovie.getBody();
        assert datawarehouseResponseMovies != null;

        return List.of(datawarehouseResponseMovies); //ResponseEntity.status(HttpStatus.FOUND).body(mwstResponseFilms);
    }

    public boolean createMovie(Film newMovie){

        List<Film> newMovies = List.of(newMovie);

        HttpEntity<List<Film>> requestEntity = this.setupHttpEntityWithMovieListBody(newMovies);

        ResponseEntity<UUID[]> responseEntity;
        try {
            responseEntity = restTemplate.exchange(dataWarehouseURI, HttpMethod.POST, requestEntity, UUID[].class);
        }catch(HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return !responseEntity.getStatusCode().isError();
    }

    public boolean changeMovie(Film changedFilm){

        HttpEntity<Film> requestEntity = this.setupHttpEntityWithMovieBody(changedFilm);

        ResponseEntity responseEntity;
        try{
            responseEntity = restTemplate.exchange(dataWarehouseURI,HttpMethod.PUT, requestEntity, ResponseEntity.class);
        }catch(HttpClientErrorException | ResourceAccessException e) {
            throw new ServiceNotAvailableException();
        }
        return !responseEntity.getStatusCode().isError();
    }

    public boolean deleteMovie(UUID filmId){

        HttpEntity<String> requestEntity = this.setupHttpEntityWithDefaultBodyAndCustomUUIDHeaderField(filmId);

        ResponseEntity responseEntity;
        try{
            responseEntity = restTemplate.exchange(dataWarehouseURI, HttpMethod.DELETE, requestEntity, ResponseEntity.class);
        }catch(HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return !responseEntity.getStatusCode().isError();
    }


    private HttpHeaders setupBasicRequestHeadersWithApplicationTypeJson(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private HttpHeaders setupBasicRequestHeadersWithUuidField(UUID uuid){
        HttpHeaders headers = new HttpHeaders();
        headers.set("UUID", uuid.toString());
        return headers;
    }

    private HttpEntity<String> setupHttpEntityDefaultStringBody(){
        return this.setupHttpEntityWithCustomStringBody("");
    }

    private HttpEntity<String> setupHttpEntityWithDefaultBodyAndCustomUUIDHeaderField(UUID uuid){
        HttpHeaders headers = this.setupBasicRequestHeadersWithUuidField(uuid);
        return new HttpEntity<>("", headers);
    }

    private HttpEntity<String> setupHttpEntityWithCustomStringBody(String body){
        HttpHeaders headers = this.setupBasicRequestHeadersWithApplicationTypeJson();
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<List<Film>> setupHttpEntityWithMovieListBody(List<Film> bodyMovieList){
        HttpHeaders headers = this.setupBasicRequestHeadersWithApplicationTypeJson();
        return new HttpEntity<>(bodyMovieList, headers);
    }

    private HttpEntity<Film> setupHttpEntityWithMovieBody(Film bodyMovie){
        HttpHeaders headers = this.setupBasicRequestHeadersWithApplicationTypeJson();
        return new HttpEntity<>(bodyMovie, headers);
    }
}