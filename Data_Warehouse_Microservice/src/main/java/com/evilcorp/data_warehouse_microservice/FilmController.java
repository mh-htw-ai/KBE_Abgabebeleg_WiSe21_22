package com.evilcorp.data_warehouse_microservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    //@Autowired
    private final FilmObjRepository filmObjRepository;

    /**
     * Funktion erstellt eine neue UUID, welche noch nicht in der Datenbank bei Filmen verwendet wurde
     * @return neue Film-UUID die noch nicht verwendet ist
     */
    @RequestMapping(value = "/newUuid", method = RequestMethod.GET)
    public ResponseEntity<String> getNewUuid(){
        log.info("getNewUuid() wird ausgefuehrt.");
        return new ResponseEntity<>(getNewUUID().toString(), HttpStatus.OK);
    }

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
        List<FilmObj> filmListe = filmObjRepository.findAllByGeloeschtFalse();
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


    /**
     * Funktion ist der Endpoint fuer das holen eines einzelnen Filmes anhand der UUID
     *
     * @param uuid   - gesuchte UUID in UUID-Format
     * @param accept - Zielformat der Response
     * @return Film-Objekt im Zielformat
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getFilm(
            @RequestHeader("UUID") UUID uuid,
            @RequestHeader(HttpHeaders.ACCEPT) String accept
    ) {
        log.info("getFilm() wird ausgeführt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept); //Ueberpruefung der Akzeptierten Formate
        if (mt == null) {
            log.info("Angefordertes Mediatype-Format wird nicht unterstützt.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
        } catch (IllegalArgumentException ex) {
            log.info("Fehlerhafte UUID wurde gesendet.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("UUDI konnte erstellt werden und wird nun in DB ermittelt.");
        if (!this.filmObjRepository.existsFilmObjByIdAndGeloeschtIsFalse(uuid)) {
            log.info("UUID(" + uuid.toString() + ") konnte nicht ermittelt werden oder ist bereits geloescht.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FilmObj film = this.filmObjRepository.findByIdAndGeloeschtIsFalse(uuid);
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


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<List<String>> postFilms(
            @RequestHeader(HttpHeaders.ACCEPT) String accept,
            @RequestHeader(HttpHeaders.CONTENT_TYPE) String ct,
            @RequestBody List<FilmObj> films
    ) {
        log.info("postFilms() wird ausgeführt.");
        log.info("Es wurden " + films.size() + " Filme uebertragen.");
        FilmObj film = null;
        UUID uuid = null;
        ArrayList<UUID> newUuids = new ArrayList<>();
        for (int i = 0; i < films.size(); i++) {
            log.info("Film(" + i + "): " + films.get(i).toString() + " wird geprueft.");
            film = films.get(i);
            if (film.getId() == null || this.filmObjRepository.existsById(film.getId())) { //UUID ist nicht vorhanden, oder ist bereits in der DB enthalten. Dann erhält der Film automatische eine neue UUID
                int abbruch = 100; //Versuche um eine neue UUID zu erstellen
                while (true) {
                    uuid = getNewUUID();
                    if (!newUuids.contains(uuid)) { //Sicherung damit RandomUUID keine Doppelte UUID fuer ein Film erstellen kann
                        newUuids.add(uuid);
                        film.setId(uuid);
                        break;
                    }
                    abbruch--;
                    if (abbruch == 0) {
                        log.error("Es konnte keine neue UUID erstellt werden.");
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                log.info("Film(" + i + "): " + films.get(i).toString() + " erhaelt neue UUID(" + film.getId().toString() + ")");
            }
        }
        log.info("Saemtliche Filme werden gespeichert.");
        List<String> responseListe = new ArrayList<>();
        for (int i = 0; i < films.size(); i++) {
            film = films.get(i);
            this.filmObjRepository.save(film);
            responseListe.add(film.getId().toString());
            log.info("Film(" + film.toString() + ") wurde in der Datenbank gespeichert.");
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(responseListe, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFilm(
            @RequestHeader("UUID") UUID uuid
    ) {
        log.info("deleteFilm() wird ausgefuehrt.");
        if (!this.filmObjRepository.existsById(uuid) || this.filmObjRepository.findById(uuid).get().isGeloescht()) {
            if (this.filmObjRepository.existsById(uuid) && this.filmObjRepository.findById(uuid).get().isGeloescht()) {
                log.info("Film mit der UUID(" + uuid.toString() + ") ist bereits geloescht wurden.");
            } else {
                log.info("Film mit der UUID(" + uuid.toString() + ") ist nicht vorhanden und konnte nicht geloescht werden.");
            }
            return new ResponseEntity<>("Fehler, die Film-UUID(" + uuid.toString() + ") ist entweder nicht in der Datenbank hinterlegt oder wurde bereits gelöscht.", HttpStatus.NOT_FOUND);
        }
        FilmObj film = this.filmObjRepository.findById(uuid).get();
        film.setGeloescht(true);
        this.filmObjRepository.save(film);
        String response = film.toString() + " wurde erfolgreich geloescht.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> putFilm(
            @RequestBody FilmObj film
    ){
        log.info("putFilm() wird ausgefuehrt.");
        if(this.filmObjRepository.existsFilmObjByIdAndGeloeschtIsFalse(film.getId())){
            FilmObj filmDB = this.filmObjRepository.findByIdAndGeloeschtIsFalse(film.getId());
            log.info("Film(" + filmDB.toString() + ") konnte ermittelt werden und wird verändert zum Film(" + film.toString() + ")");
            this.filmObjRepository.save(film);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            log.info("Film(" + film.toString() + ") konnte nicht in der DB ermittelt werden.");
            String response = "Film mit UUID(" + film.getId().toString() + ") ist nicht in der Datenbank vorhanden oder wurde bereits geloescht.";
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Funktion wandelt anhand des gewuenschten MediaTypes den gewünschten Mapper um
     *
     * @param mt - Zielt-MediaType
     * @return Mapper anhand des MediaTypes
     */
    private ObjectMapper zielformatierung(MediaType mt) {
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

    /**
     * Funktion erstellt eine neue UUID nach eigenen Muster
     *
     * @return neue UUID, die noch nicht in der DB für Filme vorhanden ist
     */
    private UUID getNewUUID() {
        UUID uuid = null;
        while (true) {
            uuid = UUID.randomUUID();
            if (!this.filmObjRepository.existsById(uuid)) {
                break;
            }
        }
        return uuid;
    }
}