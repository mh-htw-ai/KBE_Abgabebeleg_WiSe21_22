package com.example.experimental_modul.uuidtest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/testbereich")
public class Test_01 {

    private static final Logger log = LoggerFactory.getLogger(Test_01.class);

    @GetMapping()
    @RequestMapping("/test")
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/getNewUUID")
    public ResponseEntity<String> getNewUUID() {
        String ausgabe;
        UUID uuid = UUID.randomUUID();  //Erstellung einer zufaelligen UUID
        ausgabe = "Neue UUID: " + uuid;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
    return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


}
