package com.evilcorp.mwst_microservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/mwst")
public class MehrwertsteuerController {

    Logger log = LogManager.getLogger();

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
