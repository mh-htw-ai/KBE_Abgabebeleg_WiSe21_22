package com.evilcorp.data_warehouse_microservice;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final FilmRepository filmRepository;


    /**
     * Funktion ist der Endpoint um saemtliche Filminformationen der Datenbank abzurufen
     * @param accept - Header-Parameter welche die Akzeptablen Formate dann zurueck gibt
     * @return Liste mit Filmen im Zielformat
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<String> getFilmAll(
            @RequestHeader(HttpHeaders.ACCEPT) String accept){
        log.info("getFilmAll() wird ausgeführt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept);
        if(mt == null){
            return new ResponseEntity<>("Accept-Type wird nicht unterstützt.", HttpStatus.BAD_REQUEST);
        }
        List<FilmObj> filmListe = filmRepository.findAll();
        //TODO: Umwandlung in Zielformat XML oder JSON und abschicken in der Payload
        //TODO: Abschicken der Liste an Objekten
        return new ResponseEntity<>(HttpStatus.OK);
    }





}