package com.example.mwst_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/mwst")
public class MehrwertsteuerController {

    /**
     * Funktion berechnet aus den uebergebenen Preis den Mehrwert-Steuer Anteil heraus.
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     */
    @GetMapping()
    @RequestMapping("/holen/{price}")
    public ResponseEntity<String> cutMwStFromPrice(@PathVariable @DecimalMin(value="0.0", inclusive = false) double price){
       double result = MehrwertsteuerCalculator.calculateMwstValue(price);
       String outputMessage = String.valueOf(result);
       return ResponseEntity.ok(outputMessage);
    }

    /**
     * Funktion addiert die Mehrwertsteuer zu dem Preis.
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     */
    @GetMapping()
    @RequestMapping("/ermitteln/{price}")
    public ResponseEntity<String> getMwStToPrice(@PathVariable @DecimalMin(value="0.0", inclusive = false) double price){
        double result = MehrwertsteuerCalculator.calculatePriceWithMwst(price);
        String outputMessage = String.valueOf(result);
        return ResponseEntity.ok(outputMessage);
    }

    /**
     * Funktion holt den MwSt-Anteil und gibt ihn zurück
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     */
    @GetMapping()
    @RequestMapping("/steueranteil/{price}")
    public ResponseEntity<String> getMwStFromPrice(@PathVariable @DecimalMin(value="0.0", inclusive = false) double price){
        double result = MehrwertsteuerCalculator.calculateMwstFromPrice(price);
        String outputMessage = String.valueOf(result);
        return ResponseEntity.ok(outputMessage);
    }

    /*
    /**
     * Funktion berechnet aus den uebergebenen Preis den Mehrwert-Steuer Anteil heraus.
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     *
    @GetMapping()
    @RequestMapping("/holen")
    public ResponseEntity<String> cutMwStFromPrice(@RequestBody String price){
        try {
            double preis = Double.parseDouble(price);
            double erg = MehrwertsteuerCalculator.calculateMwstValue(preis);
            String ausg = String.valueOf(erg);
            return ResponseEntity.ok(ausg);
        }
        catch (NumberFormatException ex){
            return new ResponseEntity<>("Fehlerhafter Datensatz ermittelt." , HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Funktion addiert die Mehrwertsteuer zu dem Preis.
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     *
    @GetMapping()
    @RequestMapping("/ermitteln")
    public ResponseEntity<String> getMwStToPrice(@RequestBody String price){
        try {
            double preis = Double.parseDouble(price);
            double erg = MehrwertsteuerCalculator.calculatePriceWithMwst(preis);
            String ausg = String.valueOf(erg);
            return ResponseEntity.ok(ausg);
        }
        catch (NumberFormatException ex){
            return new ResponseEntity<>("Fehlerhafter Datensatz ermittelt." , HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Funktion holt den MwSt-Anteil und gibt ihn zurück
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     *
    @GetMapping()
    @RequestMapping("/steueranteil")
    public ResponseEntity<String> getMwStFromPrice(@RequestBody String price){
        try {
            double preis = Double.parseDouble(price);
            double erg = MehrwertsteuerCalculator.calculateMwstFromPrice(preis);
            String ausg = String.valueOf(erg);
            return ResponseEntity.ok(ausg);
        }
        catch (NumberFormatException ex){
            return new ResponseEntity<>("Fehlerhafter Datensatz ermittelt." ,HttpStatus.BAD_REQUEST);
        }
    }
    */
}
