package com.evilcorp.main_component_microservice.controller;

import com.evilcorp.main_component_microservice.custom_exceptions.EntityAlreadyExistsExceptions.UserAlreadyExistsException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityNotFoundExceptions.UserNotFoundException;
import com.evilcorp.main_component_microservice.model_classes.User;
import com.evilcorp.main_component_microservice.model_representations.UserRepresentation;
import com.evilcorp.main_component_microservice.entity_assembler.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class  MainUserController{

    final UserRepository userRepository;
    final UserRepresentationAssembler userAssembler;

    public MainUserController(UserRepository userRepository,
                              UserRepresentationAssembler userAssembler) {

        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }




    @GetMapping(value = "/{userId}",
            produces = "application/json")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable UUID userId){

        return userRepository.findById(userId)
                .map(user -> {
                    UserRepresentation userRepresentation = userAssembler.toModel(user)
                            .add( linkTo( methodOn(MainUserController.class).getAllUsers() ).withRel("users") );
                    return ResponseEntity.status( HttpStatus.FOUND )
                            .body( userRepresentation );
                })
                .orElse( ResponseEntity.notFound().build() );
    }




    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<UserRepresentation>> getAllUsers(){

        List<User> tempUsers = userRepository.findAll();

        return ResponseEntity.status(HttpStatus.FOUND)
                .body( userAssembler.toCollectionModel(tempUsers) );
    }



    @PostMapping(value = "/create",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> createUser( @Valid @RequestBody User newUser){
        User tempUser;

        try {
            tempUser = userRepository.save(newUser);
        }catch(Exception e){
            throw new UserAlreadyExistsException(newUser.getId());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( linkTo( methodOn(MainUserController.class).getUser( tempUser.getId() ) ).withSelfRel() );
    }




    @PutMapping(value = "/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> updateUser(@Valid @RequestBody User user){

        User tempUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));

        tempUser.update(user);
        userRepository.save(tempUser);

        return ResponseEntity.ok( linkTo( methodOn(MainUserController.class).getUser(tempUser.getId() ) ).withSelfRel() );
    }




    @DeleteMapping(value = "/delete/{userId}",
            produces = "application/json")
    public ResponseEntity deleteUser(@PathVariable UUID userId){
        User tempUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(tempUser);

        return ResponseEntity.noContent().build();
    }
}
