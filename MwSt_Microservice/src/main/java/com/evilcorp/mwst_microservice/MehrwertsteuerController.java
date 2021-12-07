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

    Logger logger = LogManager.getLogger();

    @GetMapping(value = "/json_request",
                consumes = "application/json",
                produces = "application/json")
    @ResponseBody
    public MehrwertsteuerModell calculateFromJSON(@Valid @RequestBody MehrwertsteuerModell mwstModell, BindingResult errors){
        logger.info("JSON received");
        logger.info("Calculating missing Values...");

        mwstModell.calculateMissingValues();

        logger.info("Values successfully calculated");
        logger.info("Sending response JSON...");

        return mwstModell;
    }
}
