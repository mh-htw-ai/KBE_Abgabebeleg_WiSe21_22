package com.evilcorp.main_component_microservice.services.data_warehouse_service;

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


    public ResponseEntity getFilmById(UUID movieId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<Film> responseEntity;


        try {
            responseEntity = restTemplate.exchange(dataWarehouseURI + "/uuid=" + movieId.toString(), HttpMethod.GET, entity, Film.class);
        }catch (HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie could not be found!");
        }catch (ResourceAccessException e){
            log.error("Datawarehouse-Microservice could not be reached!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity could not be created!");
        }


        Film datawarehouseResponseFilm = responseEntity.getBody();
        assert datawarehouseResponseFilm != null;
        Film mwstResponseFilm = MwStService.calculateCostWithMwstFor(datawarehouseResponseFilm);

        return ResponseEntity.status(HttpStatus.FOUND).body(mwstResponseFilm);
    }



    public ResponseEntity getAllFilms(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);


        ResponseEntity<Film[]> allFilms;
        try {
            allFilms = restTemplate.exchange(dataWarehouseURI + "/all", HttpMethod.GET, entity, Film[].class);
        }catch(HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movies could not be found!");
        }catch(ResourceAccessException e){
            log.error("Datawarehouse-Microservice could not be reached");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


        Film[] datawarehouseResponseFilms = allFilms.getBody();
        assert datawarehouseResponseFilms != null;
        Film[] mwstResponseFilms = MwStService.calculateCostWithMwstForMultipleFilms(datawarehouseResponseFilms);

        return ResponseEntity.status(HttpStatus.FOUND).body(mwstResponseFilms);
    }


    public ResponseEntity createFilm(List<Film> newFilm){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Film>> requestEntity = new HttpEntity<>(newFilm, headers);

        try {
            return restTemplate.exchange(dataWarehouseURI, HttpMethod.POST, requestEntity, UUID[].class);
        }catch(HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity could not be created!");
        }catch (ResourceAccessException e){
            log.error("Datawarehouse-Microservice could not be reached!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity could not be created!");
        }
    }


    public ResponseEntity changeMovie(Film changedFilm){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Film> requestEntity = new HttpEntity<>(changedFilm, headers);

        try{
            return restTemplate.exchange(dataWarehouseURI,HttpMethod.PUT, requestEntity, ResponseEntity.class);
        }catch(HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested movie couldn't be changed!");
        } catch (ResourceAccessException e){
            log.error("Datawarehouse-Microservice could not be reached!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity could not be created!");
        }
    }


    public ResponseEntity deleteMovie(UUID filmId){
        HttpHeaders headers = new HttpHeaders();
        headers.set("UUID", filmId.toString());
        HttpEntity<String> requestEntity = new HttpEntity<>("body", headers);

        try{
            return restTemplate.exchange(dataWarehouseURI, HttpMethod.DELETE, requestEntity, ResponseEntity.class);
        }catch(HttpClientErrorException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested movie couldn't be deleted!");
        }catch (ResourceAccessException e){
            log.error("Datawarehouse-Microservice could not be reached!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Entity could not be created!");
        }
    }
}
