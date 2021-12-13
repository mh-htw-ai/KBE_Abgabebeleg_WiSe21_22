package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.UserModelAssembler;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/main/api/v1.0/user")
public class MainUserController {

    private final UserRepository userRepository;
    private final UserModelAssembler userAssembler;

    User user = new User();
    UUID randomUUID = UUID.randomUUID();

    public MainUserController(UserRepository userRepository, UserModelAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    /**
     * test saving to database
     * @return
     */
    @GetMapping("/test")
    public User testUser(){
        user.setId(randomUUID);
        user.setUsername("testUserName");
        user.setFirstname("testFirstName");
        user.setLastname("testLastName");
        user.setEmail("testEmail");
        user.setStreet("testStreet");
        user.setStreet_number("testStreet_number");
        user.setPostcode("testPostcode");
        user.setPlaceOfResidence("testPlace");
        userRepository.saveAndFlush(user);
        return user;
    }

    /**
     * test aus databse find
     * @return
     */
    @GetMapping("/test/find")
    public @ResponseBody Iterable<User> testFindUser(){
        return userRepository.findAll();
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
        return userAssembler.toCollectionModel(userRepository.findAll());
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
        User tempUser = userRepository.save(user);
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
        return userAssembler.toModel(user);
    }

}
