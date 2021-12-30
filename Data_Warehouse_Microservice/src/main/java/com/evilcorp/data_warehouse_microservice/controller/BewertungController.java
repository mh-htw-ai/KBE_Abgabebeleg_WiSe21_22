package com.evilcorp.data_warehouse_microservice.controller;

import com.evilcorp.data_warehouse_microservice.logic.CsvImporterService;
import com.evilcorp.data_warehouse_microservice.logic.DataWarehouseLogik;
import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import com.evilcorp.data_warehouse_microservice.repository.FilmObjBewertungRepository;
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
import java.io.IOException;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/importer")
public class BewertungController {

    private static final Logger log = LoggerFactory.getLogger(BewertungController.class);

    /**
     * Interface fuer die Datenbankanbindung
     */
    private final FilmObjBewertungRepository filmObjBewertungRepository;


    /**
     * REST-Endpoint fuer saemtliche Filmbewertungen
     * Akzeptierte Ausgabe-Datentypen sind JSON oder XML
     *
     * @return uneingeschraenkte komplette Filmbewertungsliste wird zurueck gegeben
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllBewertungen(
    ){
        log.info("getAllBewertungen() wird ausgeführt.");
        List<FilmObjBewertung> list = filmObjBewertungRepository.findAll();
        ObjectMapper mapper = DataWarehouseLogik.zielformatierung(MediaType.APPLICATION_JSON);
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
     * Beispielformat des Body: "Data_Warehouse_Microservice\target\filmeObjExportData_20211225.csv"
     * Ausgangspunkt ist der Projektordner
     *
     * @return Importierte Anzahl der Bewertung aus der CSV-Datei oder das Fehlschlagen des importieren.
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getStartImport(@RequestBody String path){
        log.info("getStartImport() wird ausgeführt.");
        String res = "CSV-Datei konnte nicht importiert werden.";
        try {
            List<FilmObjBewertung> liste = CsvImporterService.importFilmObjFromCsv(path);
            log.info("Importierung aus der CSV-Datei ist abgeschlossen. Folgende Bewertungen werden in die Datenbank gespeichert.");
            FilmObjBewertung bewertung;
            int counter = 0;
            if(liste != null) {
                for (int i = 0; i < liste.size(); i++) {
                    bewertung = liste.get(i);
                    log.info("Bewertung(" + i + "):" + bewertung.toString());
                    //bewertung.createDate();
                    filmObjBewertungRepository.save(bewertung);
                    counter++;
                }
                log.info("Es wurden Insgesamt " + counter + " Bewertungen in der Datenbank gespeichert.");
                return new ResponseEntity<>("CSV-Datei wurde erfolgreich importiert. " + counter + " Bewertungen sind in die Datenbank überführt worden.", HttpStatus.OK);
            }
        }
        catch (IOException ex){
            res += " Datei konnte nicht gefunden werden.";
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }


    /**
     * Funktion für das Erstellen eines vordefinierten CSV-Exporters
     *
     * @return Speicherort der CSV-Datei oder Fehlschlag des Exports
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getStartExport(){
        log.info("getStartExport() wird ausgeführt.");
        List<FilmObjBewertung> liste = filmObjBewertungRepository.findAll();
        String path = CsvImporterService.exportFilmObjToCsv(liste);
        if(path == null){
            log.info("CSV-Datei konnte nicht erstellt werden.");
            return new ResponseEntity<>("Fehler: CSV-Datei konnte nicht erstellt werden.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String res = "CSV-Datei wurde erfolgreich erstellt." + System.lineSeparator()
                + "Speicherort: " + path;
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}

