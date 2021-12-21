package com.evilcorp.data_warehouse_microservice.logic;

import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvImporterService {

    private static final Logger log = LoggerFactory.getLogger(CsvImporterService.class);




    /**
     * Funktion exportiert die Filme in einer CSV-Datei
     *
     * @param dateiName falls die Datei ein bestimmten Namen haben soll muss dieser hier hinterlegt werden
     */
    public static boolean exportFilmObjToCsv(List<FilmObjBewertung> liste, String dateiName, String path) {
        log.info("exportFilmObjToCsv() wird ausgeführt.");
        String datei;
        String dateiEndung = ".csv";
        String trenner = "_";
        String defaultName = "filmeObjExportData";
        if (dateiName == null || dateiName.equals("")) {
            datei = defaultName;
        } else {
            datei = dateiName;
        }
        datei += trenner;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.BASIC_ISO_DATE;
        datei += now.format(df) + dateiEndung;
        FilmObjBewertung filmB;
        List<String[]> strListe = new ArrayList<>();
        strListe.add(FilmObjBewertung.getCsvHeader());
        log.info("Header: " + FilmObjBewertung.getCsvHeader());
        for (int i = 0; i < liste.size(); i++) {
            filmB = liste.get(i);
            log.info("FilmB: " + filmB.getFilmUuid() + " wird verarbeitet.");
            strListe.add(filmB.convertForCsv());
        }
        log.info("Anzahl der Zeilen: " + strListe.size());
        try (
            ICSVWriter writer = new CSVWriterBuilder(
                    new FileWriter(path + datei))
                    .withSeparator(',')
                    .build()) {
            writer.writeAll(strListe);
            } catch (IOException e) {
            e.printStackTrace();
            log.error("Fehler. Es konnte nicht in eine CSV-Datei geschrieben werden. Exception: " + e.getMessage());
        }
        log.info("Writer ist abgeschlossen.");
        log.info("CSV-Datei wurde erfolgreich erstellt.");
        return true;
    }

    public static boolean exportFilmObjToCsv(List<FilmObjBewertung> liste, String dateiName) {
        String path ="Data_Warehouse_Microservice\\target\\";
        //String path ="Data_Warehouse_Microservice\\src/main\\resources\\";
        return CsvImporterService.exportFilmObjToCsv(liste, dateiName, path);
    }

    public static boolean exportFilmObjToCsv(List<FilmObjBewertung> liste) {
        return CsvImporterService.exportFilmObjToCsv(liste, null);
    }


    public List<FilmObjBewertung> importFilmObjFromCsv(String pathFile) {
        String dateiEndung = ".csv";
        String fehlermeldung = "Datei(" + pathFile + ") konnte nicht eingelesen werden.";
        if (!pathFile.substring(pathFile.length() - dateiEndung.length(), pathFile.length()).equals(dateiEndung)) {
            log.error("Datei(" + pathFile + ") ist keine CSV-Datei.");
            return null;
        }
        List<FilmObjBewertung> liste = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(pathFile))) {
            List<String[]> r = reader.readAll();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error(fehlermeldung);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            log.error(fehlermeldung);
        }
        return liste;
    }


}
