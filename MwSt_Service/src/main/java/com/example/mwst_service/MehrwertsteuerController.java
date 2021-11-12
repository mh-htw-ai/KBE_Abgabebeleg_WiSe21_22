package com.example.mwst_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mwst")
public class MehrwertsteuerController {

    /**
     * Funktion berechnet aus den uebergebenen Preis den Mehrwert-Steuer Anteil heraus.
     * @param price = Endpreis eines Produktes
     * @return Mehrwertsteuer des Preises
     */
    @GetMapping()
    @RequestMapping("/holen")
    public ResponseEntity<String> cutMwStFromPrice(@RequestBody String price){
        try {
            double preis = Double.parseDouble(price);
            double erg = preis / 1.19;
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
     */
    @GetMapping()
    @RequestMapping("/ermitteln")
    public ResponseEntity<String> getMwStToPrice(@RequestBody String price){
        try {
            double preis = Double.parseDouble(price);
            double erg = preis * 1.19;
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
     */
    @GetMapping()
    @RequestMapping("/steueranteil")
    public ResponseEntity<String> getMwStFromPrice(@RequestBody String price){
        try {
            double preis = Double.parseDouble(price);
            double erg = preis - (preis / 1.19);
            String ausg = String.valueOf(erg);
            return ResponseEntity.ok(ausg);
        }
        catch (NumberFormatException ex){
            return new ResponseEntity<>("Fehlerhafter Datensatz ermittelt." ,HttpStatus.BAD_REQUEST);
        }
    }

}
