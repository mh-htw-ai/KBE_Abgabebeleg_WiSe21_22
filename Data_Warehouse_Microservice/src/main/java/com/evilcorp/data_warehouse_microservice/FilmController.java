package com.evilcorp.data_warehouse_microservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

//@Service("filmRestController")
@AllArgsConstructor
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final FilmObjRepository filmObjRepository;

    /*
    public FilmController(FilmObjRepository filmObjRepository) {
        this.filmObjRepository = filmObjRepository;
    }*/

    /**
     * Funktion ist der Endpoint um saemtliche Filminformationen der Datenbank abzurufen
     * @param accept - Header-Parameter welche die Akzeptablen Formate dann zurueck gibt
     * @return Liste mit Filmen im Zielformat
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<String> getFilmAll(
            @RequestHeader(HttpHeaders.ACCEPT) String accept) {
        log.info("getFilmAll() wird ausgeführt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept);
        if(mt == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<FilmObj> filmListe = filmObjRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String ausgabe = "";
        try {
            ausgabe = mapper.writeValueAsString(filmListe);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Fehler beim Umwandeln zur JSON-Liste.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO: Umwandlung in Zielformat XML oder JSON und abschicken in der Payload
        //TODO: Abschicken der Liste an Objekten
        return new ResponseEntity<>(ausgabe, HttpStatus.OK);
    }


    @RequestMapping(value = "/initial", method = RequestMethod.GET)
    public ResponseEntity<String> getInitial(
            ){
        log.info("getInitial() wird ausgeführt.");
        FilmObj f1 = FilmObj.builder()
                .uuid_film(UUID.randomUUID())
                .titel("Dune")
                .leihPreis(2.0)
                .build();
        this.filmObjRepository.save(f1);
        FilmObj f2 = FilmObj.builder()
                .uuid_film(UUID.randomUUID())
                .titel("Matrix")
                .leihPreis(3.0)
                .build();
        this.filmObjRepository.save(f2);
        FilmObj f3 = FilmObj.builder()
                .uuid_film(UUID.randomUUID())
                .titel("Crank")
                .leihPreis(4.5)
                .build();
        this.filmObjRepository.save(f3);
        String ausgabe = "Anzahl der Filme in DB: ";
        ausgabe += this.filmObjRepository.count();
        return new ResponseEntity<>(ausgabe, HttpStatus.OK);
    }

}