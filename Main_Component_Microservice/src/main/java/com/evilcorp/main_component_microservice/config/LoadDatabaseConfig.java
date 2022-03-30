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
            User user4 = new User("EinMensch",
                    "Rob",
                    "Oter",
                    "Machine@Binary.com",
                    "Datastream",
                    "101",
                    "10110",
                    "VirtualReality");
            User user5 = new User("TH3_M4SCH1N3",
                    "Allan",
                    "Turing",
                    "Allan@Turing.ok",
                    "Bandstreet",
                    "242",
                    "34324",
                    "London");
            User user6 = new User("The_Brain",
                    "Sherlock",
                    "Holmes",
                    "watson@john.co.uk",
                    "Bakerstreet",
                    "21b",
                    "12312",
                    "London");
            User user7 = new User("IAmADoctor",
                    "John",
                    "Watson",
                    "watson1@john.co.uk",
                    "Bakerstreet",
                    "21b",
                    "12312",
                    "London");
            User user8 = new User("TheBestAround",
                    "Jim",
                    "Moriarty",
                    "jim@sherlock.co.uk",
                    "Bakerstreet",
                    "22b",
                    "12312",
                    "London");
            User user9 = new User("MusterLover",
                    "Max",
                    "Musterman",
                    "Muster@Muster.de",
                    "Musterstraße",
                    "1",
                    "11111",
                    "Dresden");
            User user10 = new User("WinnieThePo",
                    "Winnifred",
                    "Pobear",
                    "Po@Leader.cn",
                    "Postreet",
                    "1",
                    "12311",
                    "Peking");
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            log.info("Done!");

            log.info("Setting up sample ratings data...");
            UUID movieId1 = UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117");
            UUID movieId2 = UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212");
            UUID movieId3 = UUID.fromString("1f79ce68-33e2-4c0e-aee5-43b148e2a457");
            UUID movieId4 = UUID.fromString("975646a3-2895-40a5-9c0a-39368cef6891");
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