package com.evilcorp.main_component_microservice;

import com.evilcorp.main_component_microservice.model_classes.Movie;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
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

    //test etup
    @Bean
    public CommandLineRunner setup(MovieRepository movieRepository){
        return (args) ->{
            movieRepository.save(new Movie("The Dark Knight"));
            movieRepository.save(new Movie("Titanic"));
            movieRepository.save(new Movie("Forest Gump"));
            movieRepository.save(new Movie("Avatar"));
            movieRepository.save(new Movie("No Country For Old Men"));
        };
    }
}
