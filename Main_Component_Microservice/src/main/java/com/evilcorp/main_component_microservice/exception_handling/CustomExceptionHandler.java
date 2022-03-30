package com.evilcorp.main_component_microservice.exception_handling;

import com.evilcorp.main_component_microservice.parsing.UuidCouldNotBeParsedException;
import com.evilcorp.main_component_microservice.movie.services.ServiceNotAvailableException;
import com.evilcorp.main_component_microservice.movie.services.csv_exporter_service.CsvCouldNotBeWrittenException;
import com.evilcorp.main_component_microservice.movie.services.data_warehouse_service.MovieCouldNotBeManipulatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {

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

    @ResponseBody
    @ExceptionHandler(CsvCouldNotBeWrittenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String handleCsvCouldNotBeWrittenException(CsvCouldNotBeWrittenException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(UuidCouldNotBeParsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleUUIDCouldNotBeParsedException(UuidCouldNotBeParsedException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(MovieCouldNotBeManipulatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleMovieCouldNotBeManipulatedException(MovieCouldNotBeManipulatedException e){return e.getMessage();}
}