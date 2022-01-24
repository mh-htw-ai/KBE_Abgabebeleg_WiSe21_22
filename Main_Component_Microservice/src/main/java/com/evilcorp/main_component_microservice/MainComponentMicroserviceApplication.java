package com.evilcorp.main_component_microservice;


import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.repositories.RentingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@SpringBootApplication
@Slf4j
public class MainComponentMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainComponentMicroserviceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataSetup(UserRepository userRepository,
                                       RatingRepository ratingRepository,
                                       RentingRepository rentingRepository){
        return (args -> {
            log.info("----------Setting up sample user!----------");
            User user1 = new User("sampleUsername",
                    "sampleFirstname",
                    "sampleLastname",
                    "sample@email.com",
                    "sampleStreet",
                    "23",
                    "10232",
                    "sampleCity");
            userRepository.save(user1);
            log.info("----------Done!----------");

            log.info("----------Setting up sample ratings!----------");
            MovieRating rating1 = new MovieRating();
            rating1.setMovieId(UUID.randomUUID());
            rating1.setRatingOwner(user1);
            rating1.setRating(5);
            ratingRepository.save(rating1);
            user1.addToRatings(rating1);
            log.info(user1.ratingList.toString());

            MovieRating rating2 = new MovieRating();
            rating2.setMovieId(UUID.randomUUID());
            rating2.setRatingOwner(user1);
            rating2.setRating(2);
            ratingRepository.save(rating2);
            user1.addToRatings(rating2);
            log.info(user1.ratingList.toString());
            log.info("----------Done!----------");

            log.info("----------Setting up sample rentings!----------");
            MovieRenting renting1 = new MovieRenting();
            renting1.setMovieId(UUID.randomUUID());
            renting1.setMovieRenter(user1);
            rentingRepository.save(renting1);
            user1.addToRentings(renting1);
            log.info(user1.rentingList.toString());
            log.info("----------Done!----------");
        });
    }

}
