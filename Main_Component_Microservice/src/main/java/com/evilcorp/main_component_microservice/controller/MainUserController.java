package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRatingRepresentationAssembler;
import com.evilcorp.main_component_microservice.entity_assembler.MovieRentingRepresentationAssembler;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.UserRepresentation;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.repositories.MovieRepository;
import com.evilcorp.main_component_microservice.repositories.RatingRepository;
import com.evilcorp.main_component_microservice.repositories.RentingRepository;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(MainUserController.userURI)
public class MainUserController extends AbstractMainController {

    final static String userURI = baseURI + "/users";

    public MainUserController(UserRepository userRepository,
                              UserRepresentationAssembler userAssembler,
                              MovieRepository movieRepository) {
        super(userRepository,
                userAssembler,
                movieRepository);
    }



    /**
     * einen nutzer per uuid finden
     * @param userId
     * @return
     */
    @GetMapping(value = "/{userId}",
            produces = "application/json")
    public ResponseEntity<UserRepresentation> findUser(@PathVariable UUID userId){
        return userRepository.findById(userId)
                .map(user -> {
                    UserRepresentation userRepresentation = userAssembler.toModel(user)
                            .add( linkTo( methodOn(MainUserController.class).findAllUsers() ).withRel("users") );
                    return ResponseEntity.status( HttpStatus.FOUND )
                            .body( userRepresentation );
                })
                .orElse( ResponseEntity.notFound().build() );
    }



    /**
     * alle nutzer finden
     * @return
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<UserRepresentation>> findAllUsers(){
        List<User> tempUsers = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND)
                .body( userAssembler.toCollectionModel(tempUsers) );
    }



    /**
     * Benutzer hinzufügen
     * @return
     */
    @PostMapping(value = "/create",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> addUser(@RequestBody User newUser){
        User tempUser = userRepository.save(newUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkTo( methodOn(MainUserController.class).findUser(tempUser.getId() ) ).withSelfRel() );
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
    public ResponseEntity<Link> changeUser(@RequestBody User user, @PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setId(userId);
        tempUser = user;
        userRepository.save(tempUser);
        return ResponseEntity.ok( linkTo( methodOn(MainUserController.class).findUser(tempUser.getId() ) ).withSelfRel() );
    }



    /**
     * loescht einen nutzer ueber die UUID
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/remove/{userId}",
            produces = "application/json")
    public ResponseEntity deleteUser(@PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(tempUser);
        return ResponseEntity.noContent().build();//"Deleted User:" + tempUser.getId().toString();
    }
}
