package com.example.experimental_modul.filmImports_test;


import classes.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/benutzer")
public class BenutzerController {

    private static final Logger log = LoggerFactory.getLogger(BenutzerController.class);

    @GetMapping()
    @RequestMapping("/all")
    public ResponseEntity<String> getAlleBenutzer() {
        log.info("getAlleBenutzer() wird ausgeführt.");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/test")
    public ResponseEntity<Film> getTest() {
        log.info("getAlleBenutzer() wird ausgeführt.");
        Film film = new Film(UUID.randomUUID());
        return new ResponseEntity<>(film, HttpStatus.OK);
    }
}
