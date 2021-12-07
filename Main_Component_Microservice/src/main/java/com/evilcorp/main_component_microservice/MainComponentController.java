package com.evilcorp.main_component_microservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main/api/v1.0")
public class MainComponentController {

    @GetMapping()
    public ResponseEntity<User> findUser(){

        return null;
    }

    @PostMapping()
    public ResponseEntity<User> addUser(){

        return null;
    }

    @PutMapping
    public ResponseEntity<User> changeUser(){

        return null;
    }

    @DeleteMapping()
    public ResponseEntity<User> deleteUser(){

        return null;
    }

}
