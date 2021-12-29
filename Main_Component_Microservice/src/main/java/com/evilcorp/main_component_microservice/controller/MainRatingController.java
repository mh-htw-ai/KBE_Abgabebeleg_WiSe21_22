package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.RatingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.RentingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(MainRatingController.ratingURI)
public class MainRatingController extends MainDefaultController {

    final static String ratingURI = baseURI + "/rate";

    public MainRatingController(UserRepository userRepository,
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

    @PutMapping(value = "/rateMovie/movie/{movieId}/user/{userId}/rating/{ratingValue}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String rateMovie(@PathVariable UUID movieId, @PathVariable UUID userId, @PathVariable int ratingValue){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        MovieRating tempRating = new MovieRating(movieId, tempUser);
        tempRating.setRating(ratingValue);
        ratingRepository.save(tempRating);



        tempUser.ratingList.add(tempRating);
        userRepository.save(tempUser);

        return "Done";//ratingAssembler.toModel(tempRating);
    }
}
