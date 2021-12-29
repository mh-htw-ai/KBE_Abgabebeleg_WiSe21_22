package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.entity_assembler.RatingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.RentingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.model_classes.Movie;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import com.evilcorp.main_component_microservice.services.csv_exporter.CsvExporterService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(MainCsvController.csvURI)
public class MainCsvController extends MainDefaultController{

    final static String csvURI = baseURI + "/csv";

    public MainCsvController(UserRepository userRepository,
                             UserModelAssembler userAssembler,
                             RatingRepository ratingRepository,
                             RatingModelAssembler ratingAssembler,
                             RentingRepository rentingRepository,
                             RentingModelAssembler rentingAssembler,
                             MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                ratingRepository,
                ratingAssembler,
                rentingRepository,
                rentingAssembler,
                movieRepository);
    }

    @PutMapping("/export")
    public void exportCsvFile() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        Movie movie1 = new Movie("The Dark Knight");
        Movie movie2 = new Movie("Titanic");
        Movie movie3 = new Movie("Forest Gump");
        Movie movie4 = new Movie("Avatar");
        Movie movie5 = new Movie("No Country For Old Men");
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);
        movieRepository.save(movie5);

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

        CsvExporterService testExporter = new CsvExporterService(this.ratingRepository);
        testExporter.exportCsvFile();
    }
}
