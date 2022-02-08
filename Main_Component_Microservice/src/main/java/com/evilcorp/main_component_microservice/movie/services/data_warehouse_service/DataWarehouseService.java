package com.evilcorp.main_component_microservice.movie.services.data_warehouse_service;

import com.evilcorp.main_component_microservice.exceptions.ServiceNotAvailableException;
import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
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
    private final RestTemplate restTemplate = new RestTemplate();

    public Movie getMovieById(UUID movieId){
        HttpEntity<String> requestEntity = this.setupHttpEntityDefaultStringBody();
        Movie datawarehouseResponseMovie = this.sendRequestForMovie(movieId, requestEntity);
        assert datawarehouseResponseMovie != null;
        return datawarehouseResponseMovie;
    }

    public List<Movie> getAllMovies(){
        HttpEntity<String> requestEntity = this.setupHttpEntityDefaultStringBody();
        Movie[] datawarehouseResponseMovies = this.sendRequestForMoviesArray(requestEntity);
        assert datawarehouseResponseMovies != null;
        return List.of(datawarehouseResponseMovies);
    }

    public boolean createMovie(Movie newMovie){
        List<Movie> listContainingNewMovie = List.of(newMovie);
        HttpEntity<List<Movie>> requestEntity = this.setupHttpEntityWithMovieListBody(listContainingNewMovie);
        ResponseEntity<UUID[]> responseEntity = this.sendRequestForMovieCreation(requestEntity);
        return !responseEntity.getStatusCode().isError();
    }

    public boolean changeMovie(Movie changedMovie){
        HttpEntity<Movie> requestEntity = this.setupHttpEntityWithMovieBody(changedMovie);
        ResponseEntity<?> responseEntity = this.sendRequestForMovieChange(requestEntity);
        return !responseEntity.getStatusCode().isError();
    }

    public boolean deleteMovie(UUID filmId){
        HttpEntity<String> requestEntity = this.setupHttpEntityWithDefaultBodyAndCustomUUIDHeaderField(filmId);
        ResponseEntity<?> responseEntity = this.sendRequestForMovieDeletion(requestEntity);
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

    private HttpEntity<String> setupHttpEntityWithCustomStringBody(String body){
        HttpHeaders headers = this.setupBasicRequestHeadersWithApplicationTypeJson();
        return new HttpEntity<>(body, headers);
    }

    private Movie sendRequestForMovie(UUID movieId, HttpEntity<String> requestEntity){
        ResponseEntity<Movie> responseEntity;
        try {
            responseEntity = restTemplate.exchange(dataWarehouseURI + "/uuid=" + movieId.toString(), HttpMethod.GET, requestEntity, Movie.class);
        }catch (HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return responseEntity.getBody();
    }

    private Movie[] sendRequestForMoviesArray(HttpEntity<String> requestEntity){
        ResponseEntity<Movie[]> allMovie;
        try {
            allMovie = restTemplate.exchange(dataWarehouseURI + "/all", HttpMethod.GET, requestEntity, Movie[].class);
        }catch(HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return allMovie.getBody();
    }


    private HttpEntity<List<Movie>> setupHttpEntityWithMovieListBody(List<Movie> bodyMovieList){
        HttpHeaders headers = this.setupBasicRequestHeadersWithApplicationTypeJson();
        return new HttpEntity<>(bodyMovieList, headers);
    }

    private ResponseEntity<UUID[]> sendRequestForMovieCreation(HttpEntity<List<Movie>> requestEntity){
        ResponseEntity<UUID[]> responseEntity;
        try {
            responseEntity = restTemplate.exchange(dataWarehouseURI, HttpMethod.POST, requestEntity, UUID[].class);
        }catch(HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return responseEntity;
    }


    private HttpEntity<Movie> setupHttpEntityWithMovieBody(Movie bodyMovie){
        HttpHeaders headers = this.setupBasicRequestHeadersWithApplicationTypeJson();
        return new HttpEntity<>(bodyMovie, headers);
    }

    private ResponseEntity<?> sendRequestForMovieChange(HttpEntity<Movie> requestEntity){
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = restTemplate.exchange(dataWarehouseURI, HttpMethod.PUT, requestEntity, ResponseEntity.class);
        }catch(HttpClientErrorException | ResourceAccessException e) {
            throw new ServiceNotAvailableException();
        }
        return responseEntity;
    }


    private HttpEntity<String> setupHttpEntityWithDefaultBodyAndCustomUUIDHeaderField(UUID uuid){
        HttpHeaders headers = this.setupBasicRequestHeadersWithUuidField(uuid);
        return new HttpEntity<>("", headers);
    }

    private ResponseEntity<?> sendRequestForMovieDeletion(HttpEntity<String> requestEntity){
        ResponseEntity<?> responseEntity;
        try{
            responseEntity = restTemplate.exchange(dataWarehouseURI, HttpMethod.DELETE, requestEntity, ResponseEntity.class);
        }catch(HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return responseEntity;
    }
}