package com.evilcorp.mwst_microservice.calculator.service;

public class TooMuchMissingValuesException extends RuntimeException{
    public TooMuchMissingValuesException(){
        super("There are not enough values bigger than zero to calculate the other missing values! Make sure the attributes are spelled correctly!");
    }
}
