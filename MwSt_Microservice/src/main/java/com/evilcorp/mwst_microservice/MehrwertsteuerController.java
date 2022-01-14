package com.evilcorp.mwst_microservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

@RestController
@Validated
@RequestMapping("/mwst")
public class MehrwertsteuerController {

    Logger log = LogManager.getLogger();

    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<String> pingTest (){
        LocalDateTime now = LocalDateTime.now();
        return new ResponseEntity<>(now.toString(), HttpStatus.OK);
    }

    @PutMapping(value = "/json_request",
                consumes = "application/json",
                produces = "application/json")
    @ResponseBody
    public MehrwertsteuerModell calculateFromJSON(@Valid @RequestBody MehrwertsteuerModell mwstModell, BindingResult errors){
        log.info("JSON received");
        log.info("Calculating missing Values...");

        mwstModell.calculateMissingValues();

        log.info("Values successfully calculated");
        log.info("Sending response JSON...");

        return mwstModell;
    }
}
