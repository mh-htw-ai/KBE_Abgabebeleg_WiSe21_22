package com.evilcorp.main_component_microservice.custom_exceptions;

import com.evilcorp.main_component_microservice.custom_exceptions.EntityAlreadyExistsExceptions.EntityAlreadyExistsException;
import com.evilcorp.main_component_microservice.custom_exceptions.EntityFoundExceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityNotFoundHandler(EntityNotFoundException e){return e.getMessage();}


    @ResponseBody
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String entityAlreadyExistsHandler(EntityAlreadyExistsException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String entityNotValidHandler(MethodArgumentNotValidException e){
        if(e.getFieldError() != null) return e.getFieldError().getDefaultMessage();
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleConstraintViolationException(ConstraintViolationException e){
        e.getConstraintViolations();
        return e.getMessage();
    }

}
