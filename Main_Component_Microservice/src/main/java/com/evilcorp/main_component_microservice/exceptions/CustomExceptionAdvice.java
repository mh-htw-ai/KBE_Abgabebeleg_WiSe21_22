package com.evilcorp.main_component_microservice.exceptions;

import com.evilcorp.main_component_microservice.exceptions.EntityAlreadyExistsExceptions.EntityAlreadyExistsException;
import com.evilcorp.main_component_microservice.exceptions.EntityNotFoundExceptions.EntityNotFoundException;
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
    String handleEntityNotFoundException(EntityNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String handleEntityAlreadyExistsException(EntityAlreadyExistsException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(ServiceNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleServiceNotAvailableException(ServiceNotAvailableException e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleEntityNotValidException(MethodArgumentNotValidException e){
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