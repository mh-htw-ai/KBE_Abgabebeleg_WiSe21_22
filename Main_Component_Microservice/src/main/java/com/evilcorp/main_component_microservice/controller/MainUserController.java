package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.RatingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.RentingModelAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/main/api/v1.0/user")
public class MainUserController {

    private final UserRepository userRepository;
    private final UserModelAssembler userAssembler;

    private final RatingRepository ratingRepository;
    private final RatingModelAssembler ratingAssembler;

    private final RentingRepository rentingRepository;
    private final RentingModelAssembler rentingAssembler;

    public MainUserController(UserRepository userRepository,
                              UserModelAssembler userAssembler,
                              RatingRepository ratingRepository,
                              RatingModelAssembler ratingAssembler,
                              RentingRepository rentingRepository,
                              RentingModelAssembler rentingAssembler) {

        this.userRepository = userRepository;
        this.userAssembler = userAssembler;

        this.ratingRepository = ratingRepository;
        this.ratingAssembler = ratingAssembler;

        this.rentingRepository = rentingRepository;
        this.rentingAssembler = rentingAssembler;
    }

    /**
     * einen nutzer per uuid finden
     * @param userId
     * @return
     */
    @GetMapping(value = "/{userId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public EntityModel<User> findUser(@PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userAssembler.toModel(tempUser);
    }

    /**
     * alle nutzer finden
     * @return
     */
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public CollectionModel<EntityModel<User>> findAllUsers(){
        List<User> tempUsers = userRepository.findAll();
        return userAssembler.toCollectionModel(tempUsers);
    }

    /**
     * Benutzer hinzufügen
     * @return
     */
    @PostMapping(value = "/create",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<User> addUser(@RequestBody User newUser){
        User tempUser = userRepository.save(newUser);
        return userAssembler.toModel(tempUser);
    }

    /**
     * veraendert den nutzer mithilfe seiner uuid und eines requestbodys mit den neuen daten
     * @param user
     * @param userId
     * @return
     */
    @PutMapping(value = "/update/{userId}",
            consumes = "application/json",
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<User> changeUser(@RequestBody User user,@PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setId(userId);
        tempUser = user;
        return userAssembler.toModel(tempUser);
    }

    /**
     * loescht einen nutzer ueber die UUID
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/remove/{userId}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EntityModel<User> deleteUser(@PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(tempUser);
        return userAssembler.toModel(tempUser);
    }

    //////////////////

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

    @PutMapping(value = "/rateMovie/movie/{movieId}/user/{userId}/rating/{ratingValue}",
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String rateMovie(@PathVariable UUID movieId, @PathVariable UUID userId, @PathVariable int ratingValue){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        MovieRating tempRating = new MovieRating(movieId, tempUser, ratingValue);
        ratingRepository.save(tempRating);

        tempUser.ratingList.add(tempRating);
        userRepository.save(tempUser);

        return "Done";//ratingAssembler.toModel(tempRating);
    }
}
