package com.evilcorp.main_component_microservice.movie.services.csv_exporter_service;


import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@EnableScheduling
public class CsvExporterService {

    private static Date lastDate = new Date();
    private static final String CSV_LOCATION = ".\\Main_Component_Microservice\\csv\\test.csv";
    private static RatingRepository ratingRepository;

    public CsvExporterService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    @Scheduled(cron = "0 0 12 * * ?")//0 seconds 0 minutes 12 hours
    public void exportRecentRatingsToCsv() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException{
        Writer writer = new FileWriter(CSV_LOCATION);

        StatefulBeanToCsv<CsvObj> beanToCsv = new StatefulBeanToCsvBuilder<CsvObj>(writer)
                .withSeparator(',')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        List<MovieRating> ratingsSinceTheLastExport = ratingRepository.findAllByRatingDateAfter(lastDate);

        List<CsvObj> list = CsvExporterService.getCsvBeanList(ratingsSinceTheLastExport);

        beanToCsv.write(list);
        beanToCsv.getCapturedExceptions();
        writer.close();

        lastDate = new Date();
    }

    private static List<CsvObj> getCsvBeanList(List<MovieRating> ratingsOfTheLastDay){
        List<CsvObj> csvObjList = new ArrayList<>();

        List<UUID> uniqueMovieIds = CsvExporterService.getMovieIdList(ratingsOfTheLastDay);
        for(UUID movieId : uniqueMovieIds){
            List<MovieRating> movieRatingsOfMovieId = getRatingsOfMovie(movieId, ratingsOfTheLastDay);
            CsvObj newCsvObj = CsvExporterService.calculateCsvBean(movieRatingsOfMovieId);
            csvObjList.add(newCsvObj);
        }

        return csvObjList;
    }

    private static List<UUID> getMovieIdList(List<MovieRating> movieRatingList){
        List<UUID> listOfUniqueMovieIds = new ArrayList<>();
        for(MovieRating rating : movieRatingList){
            UUID ratingMovieId = rating.getMovieId();
            if ( !listOfUniqueMovieIds.contains(ratingMovieId) ) listOfUniqueMovieIds.add(ratingMovieId);
        }
        return listOfUniqueMovieIds;
    }

    private static List<MovieRating> getRatingsOfMovie(UUID IdOfRequiredRatings, List<MovieRating> movieRatings){
        List<MovieRating> ratingsOfMovieX = new ArrayList<>();
        for(MovieRating rating : movieRatings){
            if(rating.getMovieId().equals(IdOfRequiredRatings)) ratingsOfMovieX.add(rating);
        }
        return ratingsOfMovieX;
    }

    private static CsvObj calculateCsvBean(List<MovieRating> ratingsOfOneMovie){
        UUID movieId = ratingsOfOneMovie.get(0).getMovieId();
        int tempAverageRating = 0;
        int tempRatingUserCount = ratingsOfOneMovie.size();
        for(MovieRating rating : ratingsOfOneMovie){
            tempAverageRating += rating.getRating();
        }
        tempAverageRating = tempAverageRating / tempRatingUserCount;

        return new CsvObj(movieId, tempAverageRating, tempRatingUserCount);
    }

}
