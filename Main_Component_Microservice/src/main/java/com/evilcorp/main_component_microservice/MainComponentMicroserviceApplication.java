package com.evilcorp.main_component_microservice;

import com.evilcorp.main_component_microservice.model_classes.Movie;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class MainComponentMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainComponentMicroserviceApplication.class, args);
    }

    //test setup
    @Bean
    public CommandLineRunner setup(MovieRepository movieRepository, UserRepository userRepository, RatingRepository ratingRepository){
        return (args) ->{

        };
    }
}
