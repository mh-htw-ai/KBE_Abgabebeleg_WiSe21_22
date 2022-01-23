package com.evilcorp.main_component_microservice.services;

import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
import com.evilcorp.main_component_microservice.services.mwst_calculator_service.MwStService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MainMovieLogicService {

    private final DataWarehouseService dataWarehouseService;
    private final ExternalApiService externalApiService;
    private final MwStService mwStService;

    public Film getMovie(UUID movieId){
        Film movieWithoutTranslatedDescriptionAndMwStIncludedPrice = dataWarehouseService.getMovieById( movieId );
        return this.getMovieWithTranslatedDescriptionAndMwStIncludedPrice( movieWithoutTranslatedDescriptionAndMwStIncludedPrice );
    }

    public List<Film> getAllMovies(){
        List<Film> movieListWithoutTranslationAndMwSt = dataWarehouseService.getAllMovies();
        return this.getMovieListWithTranslationAndMwSt( movieListWithoutTranslationAndMwSt );
    }

    public boolean createMovie(Film movie){
        return dataWarehouseService.createMovie(movie);
    }

    public boolean updateMovie(Film movie){
        return dataWarehouseService.changeMovie(movie);
    }

    public boolean deleteMovie(UUID movieId){
        return dataWarehouseService.deleteMovie(movieId);
    }


    private Film getMovieWithTranslatedDescriptionAndMwStIncludedPrice(Film movie){
        Film movieWithMwStPrice = this.calculateMoviePriceWithMwSt(movie);
        return this.translateMovieDescription(movieWithMwStPrice);
    }

    private Film translateMovieDescription(Film movie){
        return externalApiService.translateMovieDescription(movie);
    }

    private Film calculateMoviePriceWithMwSt(Film movie){
        return mwStService.calculateCostWithMwstFor(movie);
    }

    private List<Film> getMovieListWithTranslationAndMwSt(List<Film> movies){
        for(Film movie : movies){
            movie = this.getMovieWithTranslatedDescriptionAndMwStIncludedPrice(movie);
        }
        return movies;
    }
}