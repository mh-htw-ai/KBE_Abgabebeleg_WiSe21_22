package com.evilcorp.mwst_microservice.calculator.service;

public class CouldNotDetermineSteuerartException extends RuntimeException{
    public CouldNotDetermineSteuerartException(){
        super("There is no Steuerart found! Make sure the attribute is spelled correctly!");
    }
}
