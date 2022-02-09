package com.evilcorp.main_component_microservice.user.controllers;

import com.evilcorp.main_component_microservice.ParserService;
import com.evilcorp.main_component_microservice.user.UserService;
import com.evilcorp.main_component_microservice.user.representations.UserRepresentation;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/users")
public class  MainUserController{

    private final ParserService parserService;
    private final UserService userService;

    @GetMapping(value = "/{userIdString}",
            produces = "application/json")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        UserRepresentation userRepresentation = userService.getUser(userId);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userRepresentation);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<CollectionModel<UserRepresentation>> getAllUsers(){
        CollectionModel<UserRepresentation> usersRepresentation = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body( usersRepresentation );
    }

    @PostMapping(value = "/create",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> createUser(@Valid @RequestBody User newUser){
        Link linkToNewUser = userService.createUser(newUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(linkToNewUser);
    }

    @PutMapping(value = "/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Link> updateUser(@Valid @RequestBody User updatedUser){
        Link linkToUpdatedUser = userService.updateUser(updatedUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(linkToUpdatedUser);
    }

    @DeleteMapping(value = "/delete/{userIdString}",
            produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        userService.deleteUser(userId);
        return ResponseEntity
                .noContent()
                .build();
    }
}