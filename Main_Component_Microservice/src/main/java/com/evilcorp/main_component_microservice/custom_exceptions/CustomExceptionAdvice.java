package com.evilcorp.main_component_microservice.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RatingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ratingNotFoundHandler(RatingNotFoundException e){return e.getMessage();}
}
