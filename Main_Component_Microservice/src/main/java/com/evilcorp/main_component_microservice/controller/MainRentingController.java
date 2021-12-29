package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.RatingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.RentingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(MainRentingController.rentingURI)
public class MainRentingController extends MainDefaultController {

    final static String rentingURI = baseURI + "/rent";

    public MainRentingController(UserRepository userRepository,
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



    @PutMapping(value = "/rentMovie/movie/{movieId}/user/{userId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String rentMovie(@PathVariable UUID movieId, @PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        MovieRenting tempRenting = new MovieRenting(movieId, tempUser);
        rentingRepository.save(tempRenting);

        tempUser.rentingList.add(tempRenting);
        userRepository.save(tempUser);

        return "Done";//rentingAssembler.toModel(tempRenting);
    }

}
