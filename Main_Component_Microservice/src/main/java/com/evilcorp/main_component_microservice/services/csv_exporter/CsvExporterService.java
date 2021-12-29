package com.evilcorp.main_component_microservice.services.csv_exporter;


import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableScheduling
public class CsvExporterService {

    private Date lastDate = new Date();
    private final RatingRepository ratingRepository;
    private final String CSV_LOCATION = ".\\Main_Component_Microservice\\target\\test.csv";

    public CsvExporterService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    @Scheduled(cron = "0 0 12 * * ?")//0 seconds 0 minutes 12 hours
    public void exportCsvFile() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Date currentDate = new Date();
        if(this.lastDate != currentDate){
            List<MovieRating> last24hRatings = this.getRatingsBetween(lastDate, currentDate);
            List<CsvBean> csvBeans = this.getCsvBeanList(last24hRatings);
            Path csvFilePath =  Path.of(CSV_LOCATION);
            CsvExporterService.writeRowsToCsv(csvFilePath,csvBeans);
            this.lastDate = currentDate;
        }
    }

    private List<MovieRating> getRatingsBetween(Date startDate, Date endDate){
        List<MovieRating> ratingsList = ratingRepository.findAll();
        List<MovieRating> ratingsToBeRemoved = new ArrayList<>();
        for(MovieRating rating : ratingsList){
            if(rating.getDate().before(startDate) || rating.getDate().after(endDate)){
                ratingsToBeRemoved.add(rating);
            }
        }
        ratingsList.removeAll(ratingsToBeRemoved);
        return ratingsList;
    }

    private List<CsvBean> getCsvBeanList(List<MovieRating> movieRatings){
        List<UUID> movieIdList = this.getReducedMovieIdList(movieRatings);
        List<CsvBean> csvBeans = new ArrayList<>();
        for(UUID movieId : movieIdList){
            List<MovieRating> ratingsOfMovie = this.getRatingsWithSameMovieId(movieId, movieRatings);
            CsvBean currentCsvBean = this.calculateBean(movieId, ratingsOfMovie);
            csvBeans.add(currentCsvBean);
        }
        return csvBeans;
    }

    private List<UUID> getReducedMovieIdList(List<MovieRating> movieRatings){
        List<UUID> movieIdList = new ArrayList<>();
        for(MovieRating rating : movieRatings){
            UUID ratingId = rating.getMovieId();
            if(!movieIdList.contains(ratingId)) movieIdList.add(ratingId);
        }
        return movieIdList;
    }

    private List<MovieRating> getRatingsWithSameMovieId(UUID movieId, List<MovieRating> movieRatings){
        List<MovieRating> ratingsOfMovie = new ArrayList<>();
        for(MovieRating rating : movieRatings){
            if(rating.getMovieId() == movieId) ratingsOfMovie.add(rating);
        }
        return ratingsOfMovie;
    }

    private CsvBean calculateBean(UUID movieId, List<MovieRating> ratingsOfMovie){
        int ratingUserCount = ratingsOfMovie.size();
        int averageRating = 0;
        int RatingSum = 0;
        for(MovieRating rating : ratingsOfMovie){
            RatingSum += rating.getRating();
        }
        averageRating = RatingSum/ratingUserCount;

        return new CsvBean(movieId, ratingUserCount, averageRating);
    }

    private static void writeRowsToCsv(Path filePath, List<CsvBean> rows)
            throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

        FileWriter writer = new FileWriter(String.valueOf(filePath));
        ColumnPositionMappingStrategy mappingStrategy =
                new ColumnPositionMappingStrategy();
        mappingStrategy.setType(CsvBean.class);

        StatefulBeanToCsvBuilder<CsvBean> builder = new StatefulBeanToCsvBuilder(writer);
        StatefulBeanToCsv beanWriter = builder
                .withMappingStrategy(mappingStrategy)
                .withSeparator('#')
                .withQuotechar('\'')
                .build();

        beanWriter.write(rows);
        writer.close();
    }



}
