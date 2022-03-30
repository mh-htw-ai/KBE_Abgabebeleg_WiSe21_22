package com.evilcorp.mwst_microservice.calculator.controller;

import com.evilcorp.mwst_microservice.calculator.modell.MehrwertsteuerModell;
import com.evilcorp.mwst_microservice.calculator.service.MwStCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("")
public class MehrwertsteuerController {

    private final MwStCalculatorService mwStCalculatorService;

    @PutMapping(value = "/calculate_json",
                consumes = "application/json",
                produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MehrwertsteuerModell calculateFromJSON(@Valid @RequestBody MehrwertsteuerModell mwstModell){
        mwstModell = mwStCalculatorService.calculate(mwstModell);
        return mwstModell;
    }
}
