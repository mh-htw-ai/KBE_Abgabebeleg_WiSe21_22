package com.evilcorp.main_component_microservice.movie.controllers;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.movie.services.csv_exporter_service.CsvExporterService;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.DataWarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/csv")
public class MainCsvController{

    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;

    private final CsvExporterService csvExporterService;
    private final DataWarehouseService dataWarehouseService;

    public MainCsvController(UserRepository userRepository,
                             RatingRepository ratingRepository,
                             DataWarehouseService dataWarehouseService) {

        this.userRepository = userRepository;

        this.ratingRepository = ratingRepository;

        this.csvExporterService = new CsvExporterService(ratingRepository);
        this.dataWarehouseService = dataWarehouseService;
    }

    @GetMapping("/export")
    public void exportCsvFile(){
        try{
            csvExporterService.exportRecentRatingsToCsv();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @PutMapping("/setup")
    public void setupSampleData() {
        Movie movie1 = new Movie(UUID.randomUUID(), "The Dark Knight", 100.00);
        Movie movie2 = new Movie(UUID.randomUUID(), "Titanic", 1000.00);
        Movie movie3 = new Movie(UUID.randomUUID(), "Forest Gump", 50.00);
        Movie movie4 = new Movie(UUID.randomUUID(), "Avatar", 80.00);
        Movie movie5 = new Movie(UUID.randomUUID(), "No Country For Old Men", 39.90);

        User user1 = new User("Fensterputzer11", "Bill", "Gates", "windows@bill.com", "microsoftstreet", "1", "00001", "New York");
        User user2 = new User("LustigerLurch", "Herr", "Mann", "He@His.com", "TheSirStreet", "213", "21101", "Hamburg");
        userRepository.save(user1);
        userRepository.save(user2);

        MovieRating rating1 = new MovieRating(movie1.getId(),user1, 4);
        MovieRating rating2 = new MovieRating(movie2.getId(),user1, 1);
        MovieRating rating3 = new MovieRating(movie2.getId(),user2, 2);
        MovieRating rating4 = new MovieRating(movie3.getId(),user2, 1);
        ratingRepository.save(rating1);
        ratingRepository.save(rating2);
        ratingRepository.save(rating3);
        ratingRepository.save(rating4);
    }
}
