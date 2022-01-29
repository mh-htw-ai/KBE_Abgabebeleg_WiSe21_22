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
        return this.getMovieWithTranslatedDescriptionAndMwStIncludedPrice( movieWithoutTranslatedDescriptionAndMwStIncludedPrice );
    }

    public List<Movie> getAllMovies(){
        List<Movie> movieListWithoutTranslationAndMwSt = dataWarehouseService.getAllMovies();
        return this.getMovieListWithTranslationAndMwSt( movieListWithoutTranslationAndMwSt );
    }

    public boolean createMovie(Movie movie){
        return dataWarehouseService.createMovie(movie);
    }

    public boolean updateMovie(Movie movie){
        return dataWarehouseService.changeMovie(movie);
    }

    public boolean deleteMovie(UUID movieId){
        return dataWarehouseService.deleteMovie(movieId);
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