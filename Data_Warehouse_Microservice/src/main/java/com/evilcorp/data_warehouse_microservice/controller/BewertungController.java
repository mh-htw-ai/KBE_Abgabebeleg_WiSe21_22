package com.evilcorp.data_warehouse_microservice.controller;

import com.evilcorp.data_warehouse_microservice.logic.CsvImporterService;
import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import com.evilcorp.data_warehouse_microservice.repository.FilmObjBewertungRepository;
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
@RequestMapping("/importer")
public class BewertungController {

    private static final Logger log = LoggerFactory.getLogger(BewertungController.class);

    private final FilmObjBewertungRepository filmObjBewertungRepository;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllBewertungen(
    ){
        log.info("getAllBewertungen() wird ausgeführt.");
        List<FilmObjBewertung> list = filmObjBewertungRepository.findAll();
        ObjectMapper mapper = zielformatierung(MediaType.APPLICATION_JSON);
        String ausgabe = "";
        try {
            ausgabe = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.error("Fehler beim Umwandeln in JSON");
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(ausgabe, header, HttpStatus.OK);
    }


    /**
     * Funktion startet das Importieren einer CSV-Datei
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getStartImport(){
        log.info("getStartImport() wird ausgeführt.");
        //TODO: IMplementierung für das Importieren von Filmbewertungen.


        return new ResponseEntity<>("CSV-Datei wurde erfolgreich erstellt.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //TODO: Exporter-Endpoint hier dient nur zu Textzwecken und muss entfernt werden.
    /**
     * Testfunktion für das Erstellen eines vordefinierten CSV-Exporters
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getStartExport(){
        log.info("getStartExport() wird ausgeführt.");
        List<FilmObjBewertung> liste = filmObjBewertungRepository.findAll();
        if(!CsvImporterService.exportFilmObjToCsv(liste)){
            log.info("CSV-Datei konnte nicht erstellt werden.");
            return new ResponseEntity<>("Fehler: CSV-Datei konnte nicht erstellt werden.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("CSV-Datei wurde erfolgreich erstellt.", HttpStatus.OK);
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
}

