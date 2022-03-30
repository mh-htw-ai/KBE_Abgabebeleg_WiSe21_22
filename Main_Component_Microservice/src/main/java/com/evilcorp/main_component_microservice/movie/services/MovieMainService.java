package com.evilcorp.main_component_microservice.movie.services;

import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.movie.services.external_api_service.ExternalApiService;
import com.evilcorp.main_component_microservice.movie.services.mwst_calculator_service.MwStService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MovieMainService {

    private final DataWarehouseService dataWarehouseService;
    private final ExternalApiService externalApiService;
    private final MwStService mwStService;

    public Movie getMovie(UUID movieId){
        Movie movieWithoutTranslatedDescriptionAndMwStIncludedPrice = dataWarehouseService.getMovieById( movieId );
        if(movieWithoutTranslatedDescriptionAndMwStIncludedPrice == null) return null;
        return this.getMovieWithTranslatedDescriptionAndMwStIncludedPrice( movieWithoutTranslatedDescriptionAndMwStIncludedPrice );
    }

    public List<Movie> getAllMovies(){
        List<Movie> movieListWithoutTranslationAndMwSt = dataWarehouseService.getAllMovies();
        if(movieListWithoutTranslationAndMwSt == null) return null;
        return this.getMovieListWithTranslationAndMwSt( movieListWithoutTranslationAndMwSt );
    }

    public void createMovie(Movie movie){
        dataWarehouseService.createMovie(movie);
    }

    public void updateMovie(Movie movie){
        dataWarehouseService.changeMovie(movie);
    }

    public void deleteMovie(UUID movieId){
        dataWarehouseService.deleteMovie(movieId);
    }


    private Movie getMovieWithTranslatedDescriptionAndMwStIncludedPrice(Movie movie){
        Movie movieWithMwStPrice = this.calculateMoviePriceWithMwSt(movie);
        return this.translateMovieDescription(movieWithMwStPrice);
    }

    private Movie translateMovieDescription(Movie movie){
        return externalApiService.translateMovieDescriptions(movie);
    }

    private Movie calculateMoviePriceWithMwSt(Movie movie){
        return mwStService.calculateCostWithMwstFor(movie);
    }

    private List<Movie> getMovieListWithTranslationAndMwSt(List<Movie> movies){
        for(Movie movie : movies){
            movie = this.getMovieWithTranslatedDescriptionAndMwStIncludedPrice(movie);
        }
        return movies;
    }
}