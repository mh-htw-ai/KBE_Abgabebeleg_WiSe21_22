package com.evilcorp.mwst_microservice;

import com.evilcorp.mwst_microservice.calculator.modell.MehrwertsteuerModell;
import com.evilcorp.mwst_microservice.calculator.modell.Mehrwertsteuerart;
import com.evilcorp.mwst_microservice.calculator.service.CouldNotDetermineSteuerartException;
import com.evilcorp.mwst_microservice.calculator.service.MwStCalculatorService;
import com.evilcorp.mwst_microservice.calculator.service.TooMuchMissingValuesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MwStCalculatorServiceTests {

    MwStCalculatorService calculatorService;
    MehrwertsteuerModell mwstModell;

    @BeforeEach
    void setup(){
        calculatorService = new MwStCalculatorService();
        mwstModell = new MehrwertsteuerModell(0f, 0f, 0f, Mehrwertsteuerart.STANDARD);
    }

    @Test
    void calculateTestForTooMuchMissingValuesException(){
        assertThrows(
                TooMuchMissingValuesException.class,
                () -> calculatorService.calculate(mwstModell));
    }

    @Test
    void calculateTestForCouldNotDetermineSteuerartException(){
        mwstModell.setSteuertyp(null);
        assertThrows(
                CouldNotDetermineSteuerartException.class,
                () -> calculatorService.calculate(mwstModell));
    }

    @Test
    void calculateTestWithArtikelpreisGivenAndStandardSteuerart(){
        mwstModell.setArtikelpreis(42.69f);
        mwstModell = calculatorService.calculate(mwstModell);
        assertAll(
                () -> assertEquals(mwstModell.getArtikelpreis(), 42.69f),
                () -> assertEquals(mwstModell.getSteueranteil(), 8.11f),
                () -> assertEquals(mwstModell.getArtMitSteuer(), 50.8f)
        );
    }

    @Test
    void calculateTestWithArtikelpreisGivenAndReduziertSteuerart(){
        mwstModell.setSteuertyp(Mehrwertsteuerart.REDUZIERT);
        mwstModell.setArtikelpreis(598.58f);
        mwstModell = calculatorService.calculate(mwstModell);
        assertAll(
                () -> assertEquals(mwstModell.getArtikelpreis(), 598.58f),
                () -> assertEquals(mwstModell.getSteueranteil(), 41.9f),
                () -> assertEquals(mwstModell.getArtMitSteuer(), 640.48f)
        );
    }

    @Test
    void calculateTestWithArtMitSteuerGivenAndStandardSteuerart(){
        mwstModell.setArtMitSteuer(712.31f);
        mwstModell = calculatorService.calculate(mwstModell);
        assertAll(
                () -> assertEquals(mwstModell.getArtikelpreis(), 598.58f),
                () -> assertEquals(mwstModell.getSteueranteil(), 113.73f),
                () -> assertEquals(mwstModell.getArtMitSteuer(), 712.31f)
        );
    }

    @Test
    void calculateTestWithArtMitSteuerGivenAndReduziertSteuerart(){
        mwstModell.setSteuertyp(Mehrwertsteuerart.REDUZIERT);
        mwstModell.setArtMitSteuer(314.15f);
        mwstModell = calculatorService.calculate(mwstModell);
        assertAll(
                () -> assertEquals(mwstModell.getArtikelpreis(), 293.6f),
                () -> assertEquals(mwstModell.getSteueranteil(), 20.55f),
                () -> assertEquals(mwstModell.getArtMitSteuer(), 314.15f)
        );
    }
}