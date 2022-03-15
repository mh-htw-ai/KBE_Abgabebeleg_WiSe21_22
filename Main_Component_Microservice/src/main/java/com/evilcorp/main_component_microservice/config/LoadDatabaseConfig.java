package com.evilcorp.main_component_microservice.config;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.repositories.RentingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Slf4j
public class LoadDatabaseConfig {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   RentingRepository rentingRepository,
                                   RatingRepository ratingRepository){
        return args -> {
            log.info("Setting up sample user data...");
            User user1 = new User("SaviorOfDaUniverse",
                    "Eduard",
                    "Laser",
                    "Laser@rbtv.com",
                    "Heinrichstraße",
                    "9-11",
                    "22769",
                    "Hamburg");
            User user2 = new User("CountingCount",
                    "Graf",
                    "Zahl",
                    "theCount@sesa.me",
                    "Sesamestreet",
                    "42",
                    "42424",
                    "Sesamecity");
            User user3 = new User("ThePirate390",
                    "Guybrush",
                    "Threepwood",
                    "theGuy@theSea.com",
                    "Junglestreet",
                    "1",
                    "94090",
                    "ThePirateIsle");
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            log.info("Done!");

            log.info("Setting up sample ratings data...");
            UUID movieId1 = UUID.randomUUID();
            UUID movieId2 = UUID.randomUUID();
            UUID movieId3 = UUID.randomUUID();
            UUID movieId4 = UUID.randomUUID();
            MovieRating rating1 = new MovieRating(movieId1, user1.getId(), 3);
            MovieRating rating2 = new MovieRating(movieId2, user1.getId(), 5);
            MovieRating rating3 = new MovieRating(movieId3, user1.getId(), 1);
            MovieRating rating4 = new MovieRating(movieId1, user2.getId(), 3);
            MovieRating rating5 = new MovieRating(movieId4, user2.getId(), 4);
            MovieRating rating6 = new MovieRating(movieId4, user3.getId(), 5);
            ratingRepository.save(rating1);
            ratingRepository.save(rating2);
            ratingRepository.save(rating3);
            ratingRepository.save(rating4);
            ratingRepository.save(rating5);
            ratingRepository.save(rating6);
            log.info("Done!");

            log.info("Setting up sample rentings data...");
            MovieRenting renting1 = new MovieRenting(movieId1, user1.getId());
            MovieRenting renting2 = new MovieRenting(movieId2, user1.getId());
            MovieRenting renting3 = new MovieRenting(movieId1, user2.getId());
            rentingRepository.save(renting1);
            rentingRepository.save(renting2);
            rentingRepository.save(renting3);
            log.info("Done!");
        };
    }
}