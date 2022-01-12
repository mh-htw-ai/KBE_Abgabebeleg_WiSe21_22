package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.MovieRatingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.csv_exporter.CsvExporterService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.DataWarehouseService;
import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        Film movie1 = new Film(UUID.randomUUID(), "The Dark Knight", 100.00);
        Film movie2 = new Film(UUID.randomUUID(), "Titanic", 1000.00);
        Film movie3 = new Film(UUID.randomUUID(), "Forest Gump", 50.00);
        Film movie4 = new Film(UUID.randomUUID(), "Avatar", 80.00);
        Film movie5 = new Film(UUID.randomUUID(), "No Country For Old Men", 39.90);

        User user1 = new User("Fensterputzer11", "Bill", "Gates", "windows@bill.com", "microsoftstreet", "1", "00001", "New York");
        User user2 = new User("LustigerLurch", "Herr", "Mann", "He@His.com", "TheSirStreet", "213", "21101", "Hamburg");
        userRepository.save(user1);
        userRepository.save(user2);

        MovieRating rating1 = new MovieRating(movie1.getId(),user1);
        MovieRating rating2 = new MovieRating(movie2.getId(),user1);
        MovieRating rating3 = new MovieRating(movie2.getId(),user2);
        MovieRating rating4 = new MovieRating(movie3.getId(),user2);
        rating1.setRating(5);
        rating2.setRating(3);
        rating3.setRating(5);
        rating4.setRating(1);
        ratingRepository.save(rating1);
        ratingRepository.save(rating2);
        ratingRepository.save(rating3);
        ratingRepository.save(rating4);
    }
}
