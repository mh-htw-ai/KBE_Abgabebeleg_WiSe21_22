package com.evilcorp.main_component_microservice.user.controllers;

import com.evilcorp.main_component_microservice.parsing.ParserService;
import com.evilcorp.main_component_microservice.user.services.UserService;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<User> getUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        User user = userService.getUser(userId);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( user );
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( users );
    }

    @PostMapping(value = "/create",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<UUID> createUser(@Valid @RequestBody User newUser){
       UUID newUserId = userService.createUser(newUser);
        return ResponseEntity
                .status( HttpStatus.CREATED )
                .body( newUserId );
    }

    @PutMapping(value = "/update",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User userUpdate){
        User updatedUser = userService.updateUser(userUpdate);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( updatedUser );
    }

    @DeleteMapping(value = "/delete/{userIdString}")
    public ResponseEntity<?> deleteUser(@PathVariable String userIdString){
        UUID userId = parserService.parseStringToUUID(userIdString);
        userService.deleteUser(userId);
        return ResponseEntity
                .noContent()
                .build();
    }
}