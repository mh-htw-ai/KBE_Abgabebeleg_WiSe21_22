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
import java.util.UUID;

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


    @RequestMapping(value = "/initial", method = RequestMethod.GET)
    public ResponseEntity<String> getInitial(
            ){
        log.info("getInitial() wird ausgeführt.");
        FilmObj f1 = FilmObj.builder()
                .uuid_Film(UUID.randomUUID())
                .titel("Dune")
                .leihPreis(2.0)
                .build();
        this.filmRepository.save(f1);
        FilmObj f2 = FilmObj.builder()
                .uuid_Film(UUID.randomUUID())
                .titel("Matrix")
                .leihPreis(3.0)
                .build();
        this.filmRepository.save(f2);
        FilmObj f3 = FilmObj.builder()
                .uuid_Film(UUID.randomUUID())
                .titel("Crank")
                .leihPreis(4.5)
                .build();
        this.filmRepository.save(f3);
        String ausgabe = "Anzahl der Filme in DB: ";
        ausgabe += this.filmRepository.count();
        return new ResponseEntity<>(ausgabe, HttpStatus.OK);
    }

}