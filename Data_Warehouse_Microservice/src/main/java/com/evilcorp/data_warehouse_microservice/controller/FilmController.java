package com.evilcorp.data_warehouse_microservice.controller;

import com.evilcorp.data_warehouse_microservice.logic.DataWarehouseLogik;
import com.evilcorp.data_warehouse_microservice.model.FilmObj;
import com.evilcorp.data_warehouse_microservice.repository.FilmObjRepository;
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

    private final FilmObjRepository filmObjRepository;


    /**
     * Funktion erstellt eine neue UUID, welche noch nicht in der Datenbank bei Filmen verwendet wurde
     *
     * @return neue Film-UUID die noch nicht verwendet ist
     */
    @RequestMapping(value = "/newUuid", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getNewUuid() {
        log.info("getNewUuid() wird ausgefuehrt.");
        UUID uuid = getNewUUID();
        log.info("getNewUuid: Die neue UUID(" + uuid.toString() + ") wird zurueck geschickt.");
        return new ResponseEntity<>(uuid.toString(), HttpStatus.OK);
    }


    /**
     * Funktion ist der Endpoint um saemtliche Filminformationen der Datenbank abzurufen
     *
     * @param accept - Header-Parameter welche die Akzeptablen Formate dann zurueck gibt
     * @return Liste mit Filmen im Zielformat
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getFilmAll(
            @RequestHeader(HttpHeaders.ACCEPT) MediaType accept
    ) {
        log.info("getFilmAll() wird ausgeführt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept); //Ueberpruefung der Akzeptierten Formate
        if (mt == null) {
            log.info("Angefordertes Mediatype-Format wird nicht unterstützt.");
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
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
        log.info("getFilmAll(): Es werden insgesamt " + filmListe.size() + " Filme in einer Liste zurueck geschickt.");
        return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


    /**
     * Funktion ist der Endpoint fuer das holen eines einzelnen Filmes anhand der UUID
     *
     * @param uuid   - gesuchte UUID in UUID-Format
     * @param accept - Zielformat der Response
     * @return Film-Objekt im Zielformat
     */
    @RequestMapping(
            method = RequestMethod.GET
            , headers = "UUID"
    )
    @ResponseBody
    public ResponseEntity<String> getFilm(
            @RequestHeader("UUID") UUID uuid,
            @RequestHeader(HttpHeaders.ACCEPT) MediaType accept
    ) {
        log.info("getFilm() wird ausgeführt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept); //Ueberpruefung der Akzeptierten Formate
        if (mt == null) {
            log.info("Angefordertes Mediatype-Format wird nicht unterstützt.");
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
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
            log.error("Fehler beim Umwandeln zur " + mt + "-Objekt.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mt);
        log.info("getFilm(): Film(" + film + ") wurde ermittelt und zurueckgeschickt.");
        return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


    /**
     * Funktion holt saemtliche Filme anhand des Titels
     *
     * @param titel des gesuchten Filmes
     * @return Liste mit Filmen
     */
    @RequestMapping(
            method = RequestMethod.GET
            , params = "title"
    )
    @ResponseBody
    public ResponseEntity<String> getFilmByTitle(
            @RequestParam("title") String titel
            , @RequestHeader(HttpHeaders.ACCEPT) MediaType accept
    ){
        log.info("getFilmByTitle() wird ausgefuehrt.");
        MediaType mt = DataWarehouseLogik.checkAccept(accept);
        if (mt == null) {
            log.info("Angefordertes Mediatype-Format wird nicht unterstützt.");
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        List<FilmObj> filmListe = this.filmObjRepository.findAllByTitelContaining(titel);
        if(filmListe.size() == 0){
            String res = "Es konnte kein Titel mit dem Inhalt(" + titel + ") gefunden werden.";
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
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
        log.info("getFilmByTitle(): Es wurden insgesamt " + filmListe.size() + " Filme mit dem Titel(" + titel + ") gefunden und zurueckgeschickt." );
        return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


    /**
     * POST-Request fuer das Hinzufuegen von weiteren Filmen
     * - Wenn die neuen Filme eine UUID mitbringen wird diese geprueft und bei Doppelung automatisch ersetzt
     *
     * @param films Filme in einer JSON-Liste
     * @return Liste mit den UUID's der neuen Filme in der Datenbank
     */
    @RequestMapping(
            method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<UUID>> postFilms(
            @RequestBody List<FilmObj> films
    ) {
        log.info("postFilms() wird ausgeführt.");
        log.info("Es wurden " + films.size() + " Filme uebertragen.");
        FilmObj film;
        UUID uuid;
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
        List<UUID> responseListe = new ArrayList<>();
        int i = 0;
        while (i < films.size()) {
            film = films.get(i);
            this.filmObjRepository.save(film);
            responseListe.add(film.getId());
            log.info("Film(" + film + ") wurde in der Datenbank gespeichert.");
            i++;
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        log.info("postFilms(): Es wurden " + responseListe.size() + " Filme erfolgreich in die Datenbank gespeichert.");
        return new ResponseEntity<>(responseListe, header, HttpStatus.OK);
    }


    /**
     * DELETE-Request fuer das Loeschen von Filmen in der Datenbank
     * Die Funktion loescht NICHT die Filme aus der Datenbank, sondern markiert diese als geloescht. Damit werden die markierten Filme nicht mehr ausgegeben.
     *
     * @param uuid - UUID des zu loeschenden Filmes
     * @return No Content wenn der Film erfolgreich geloescht wurde
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteFilm(
            @RequestHeader("UUID") UUID uuid
    ) {
        log.info("deleteFilm() wird ausgefuehrt.");
        if (!this.filmObjRepository.existsFilmObjByIdAndGeloeschtIsFalse(uuid)) {
            log.info("Film-UUID(" + uuid + ") ist nicht in der Film-Datenbank vorhanden.");
            return new ResponseEntity<>("Fehler, die Film-UUID(" + uuid + ") ist entweder nicht in der Datenbank hinterlegt oder wurde bereits gelöscht.", HttpStatus.NOT_FOUND);
        }
        FilmObj film = this.filmObjRepository.findByIdAndGeloeschtIsFalse(uuid);
        film.setGeloescht(true);
        this.filmObjRepository.save(film);
        log.info("deleteFilm(): Der Film (" + film + ") wurde erfolgreich als geloescht markiert.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * PUT-Request um ein Film zu veraendern.
     * - Es wird ermittelt, ob der Film in der Datenbank vorhanden und nicht geloescht ist und aendert den kompletten Datensatz.
     * @param film - FilmObj-Objekt, muss die gleiche UUID von dem Ziel-Film besitzen um diesen dann zu aktualisieren.
     * @return No Content wenn der Film erfolgreich aktualisiert wurde.
     */
    @RequestMapping(
            method = RequestMethod.PUT
            , consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<String> putFilm(
            @RequestBody FilmObj film
    ) {
        log.info("putFilm() wird ausgefuehrt.");
        if (!this.filmObjRepository.existsFilmObjByIdAndGeloeschtIsFalse(film.getId())) {
            log.info("Film(" + film + ") konnte nicht in der DB ermittelt werden.");
            String response = "Film mit UUID(" + film.getId().toString() + ") ist nicht in der Datenbank vorhanden oder wurde bereits geloescht.";
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        FilmObj filmDB = this.filmObjRepository.findByIdAndGeloeschtIsFalse(film.getId());
        log.info("Film(" + filmDB.toString() + ") konnte ermittelt werden und wird verändert zum Film(" + film + ")");
        this.filmObjRepository.save(film);
        log.info("putFilm(): Der Film(" + filmDB + ") wurde erfolgreich zum Film(" + film + ") aktualisiert.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (this.filmObjRepository.existsById(uuid));
        return uuid;
    }


}