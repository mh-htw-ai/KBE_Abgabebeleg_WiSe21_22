package com.evilcorp.data_warehouse_microservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private final FilmObjRepository filmObjRepository;

    /**
     * Funktion ist der Endpoint um saemtliche Filminformationen der Datenbank abzurufen
     *
     * @param accept - Header-Parameter welche die Akzeptablen Formate dann zurueck gibt
     * @return Liste mit Filmen im Zielformat
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<String> getFilmAll(
            @RequestHeader(HttpHeaders.ACCEPT) String accept
    ) {
        log.info("getFilmAll() wird ausgeführt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept); //Ueberpruefung der Akzeptierten Formate
        if (mt == null) {
            log.info("Angefordertes Mediatype-Format wird nicht unterstützt.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<FilmObj> filmListe = filmObjRepository.findAll();
        String ausgabe;
        try {
            ObjectMapper mapper = zielformatierung(mt);
            ausgabe = mapper.writeValueAsString(filmListe);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Fehler beim Umwandeln zur " + mt.getType() + "-Liste.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mt);
        return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getFilm(
            @RequestHeader("UUID") String uuidStr,
            @RequestHeader(HttpHeaders.ACCEPT) String accept
    ) {
        log.info("getFilm() wird ausgeführt.");
        //TODO: GET-Request für einzelnen Film
        MediaType mt = DataWarehouseLogik.checkAccept(accept); //Ueberpruefung der Akzeptierten Formate
        if (mt == null) {
            log.info("Angefordertes Mediatype-Format wird nicht unterstützt.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UUID uuid;
        try {
            uuid = UUID.fromString(uuidStr);
        } catch (IllegalArgumentException ex) {
            log.info("Fehlerhafte UUID wurde gesendet.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("UUDI konnte erstellt werden und wird nun in DB ermittelt.");
        if(!this.filmObjRepository.existsById(uuid)){
            log.info("UUID(" + uuid.toString() + ") konnte nicht ermittelt werden.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //FilmObj film = this.filmObjRepository.getById(uuid); // Problematisch, da mit Referenzierung bei Springboot gearbeitet wird.
        FilmObj film = this.filmObjRepository.findById(uuid).get(); // Muss so verwendet werden, da ansosnten die Referenzierung probleme macht.
        String ausgabe;
        try {
            ObjectMapper mapper = zielformatierung(mt);
            ausgabe = mapper.writeValueAsString(film);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Fehler beim Umwandeln zur " + mt.toString() + "-Objekt.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mt);
        return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }

    //TODO: POST-Request für ein neuen Film

    //TODO: DELETE-Request für das Löschen eines Filmes

    //TODO: PUT-Request für das Aktualisieren eines Filmes


    /**
     * Funktion wandelt anhand des gewuenschten MediaTypes den gewünschten Mapper um
     * @param mt - Zielt-MediaType
     * @return Mapper anhand des MediaTypes
     */
    private ObjectMapper zielformatierung (MediaType mt) {
        ObjectMapper mapper;
        System.out.println("Zielformat: " + mt.toString());
        if (mt.equals(MediaType.APPLICATION_XML)) {
            mapper = new XmlMapper();
            System.out.println("Umwandlung in XML.");
        } else {
            mapper = new JsonMapper();
            System.out.println("Umwandlung in JSON.");
        }
        return mapper;
    }

}