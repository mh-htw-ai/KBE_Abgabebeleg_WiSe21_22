package com.example.experimental_modul.jackson;

import com.example.experimental_modul.customer_test.CustomerController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/car")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private Car car1 = Car.builder().color("yellow").type("Porsche").build();
    private Car car2 = Car.builder().color("blue").type("VW").build();
    private Car car3 = Car.builder().color("red").type("Lamborgini").build();
    private Car car4 = Car.builder().color("green").type("Opel").build();

    private ArrayList<Car> liste = new ArrayList<>(Arrays.asList(car1, car2, car3, car4));


    @RequestMapping(
            value = "/test",
            method = RequestMethod.GET)
    public ResponseEntity<String> getTest(){
        log.info("getTest() wird ausgeführt.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Funktion soll die gesamte Liste der Autos in JSON-Format im Body zurueckschicken
     */
    @RequestMapping(
            value="/json",
            method= RequestMethod.GET)
    public ResponseEntity<String> getCarInJSON(
            //@RequestParam int index
            //, @PathVariable("{index}") String id
            //, @RequestHeader(HttpHeaders.ACCEPT)MediaType mediaType
            ){
        log.info("getCarInJSON() wird ausgeführt.");
            String ausgabe = "";
            int index = 0;
            Car car = liste.get(index);
            ObjectMapper mapper = new ObjectMapper();
            try {
                ausgabe = mapper.writeValueAsString(car);
                log.info("getCarInJSON(): Ergebnis = " + ausgabe);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


    /**
     * Funktion soll die gesamte Liste der Autos in JSON-Format im Body zurueckschicken
     *//*
    @RequestMapping("/listeJSON")
    @GetMapping
    public ResponseEntity<String> getCarListeInJSON(){
//TODO: gesamte Liste in JSON Format als String zurueckgeben.

        return new ResponseEntity<>(HttpStatus.OK);
    }
*/

    //TODO: Hinzufügen in die Liste ermöglichen
    //TODO: Jackson Liste in JSON Format
    //TODO: Jackson Liste in XML Format
    // TODO: CSV-Formatierung erstellen

}
