package com.evilcorp.mwst_microservice.calculator;

import com.evilcorp.mwst_microservice.calculator.service.CouldNotDetermineSteuerartException;
import com.evilcorp.mwst_microservice.calculator.service.TooMuchMissingValuesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleConstraintViolationException(ConstraintViolationException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TooMuchMissingValuesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleToMuchMissingValuesException(TooMuchMissingValuesException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CouldNotDetermineSteuerartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleCouldNotDetermineSteuerartException(CouldNotDetermineSteuerartException e){return e.getMessage();}
}