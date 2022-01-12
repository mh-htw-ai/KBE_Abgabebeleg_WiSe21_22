package com.example.experimental_modul.gateway_test;


import com.example.experimental_modul.customer_test.CustomerController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor // Erstellt ein Konstruktor mit saemtlichen benoetigten Attributen
@RestController
@RequestMapping("/gw")
public class GWController {

    private static final Logger log = LoggerFactory.getLogger(GWController.class);


    @RequestMapping(value = "/tester"
            , method = RequestMethod.GET
            , headers = "hello")
    public ResponseEntity<String> getGWTest(
            @RequestHeader("hello") String hello
    ) {
        log.info("===============================================================");
        log.info("===============================================================");
        log.info("===============================================================");
        log.info("getGWTest() wird ausgeführt.");
        log.info("Die Weiterleitung war hiermit erfolgreich.");
        log.info("Hello: " + hello);
        log.info("===============================================================");
        log.info("===============================================================");
        log.info("===============================================================");
        return new ResponseEntity<>("Ausführung MIT Header(Hello)", HttpStatus.OK);
    }


    @RequestMapping(value = "/tester"
            , method = RequestMethod.GET)
    public ResponseEntity<String> getGWTest1(
    ) {
        log.info("===============================================================");
        log.info("===============================================================");
        log.info("===============================================================");
        log.info("getGWTest1() wird ausgeführt.");
        log.info("Die Weiterleitung war hiermit erfolgreich.");
        log.info("===============================================================");
        log.info("===============================================================");
        log.info("===============================================================");
        return new ResponseEntity<>("Ausführung ohne Header(Hello)", HttpStatus.OK);
    }


}
