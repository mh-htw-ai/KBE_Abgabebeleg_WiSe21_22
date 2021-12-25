package com.evilcorp.data_warehouse_microservice.logic;

import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CsvImporterService {

    private static final Logger log = LoggerFactory.getLogger(CsvImporterService.class);


    /**
     * Funktion exportiert die Filme in einer CSV-Datei
     *
     * @param dateiName falls die Datei ein bestimmten Namen haben soll muss dieser hier hinterlegt werden
     */
    public static String exportFilmObjToCsv(List<FilmObjBewertung> liste, String dateiName, String path) {
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
            return null;
        }
        log.info("Writer ist abgeschlossen.");
        log.info("CSV-Datei wurde erfolgreich erstellt.");
        return path + datei;
    }

    public static String exportFilmObjToCsv(List<FilmObjBewertung> liste, String dateiName) {
        String path = "Data_Warehouse_Microservice\\target\\";
        //String path ="Data_Warehouse_Microservice\\src/main\\resources\\";
        return CsvImporterService.exportFilmObjToCsv(liste, dateiName, path);
    }

    public static String exportFilmObjToCsv(List<FilmObjBewertung> liste) {
        return CsvImporterService.exportFilmObjToCsv(liste, null);
    }


//TODO: Implementierung ist Fehlerhaft

    /**
     * Funktion importiert die CSV-Datei und wandelt die Eintraege in neue Einträge für die FilmBewertungs-Datenbank
     *
     * @param pathFile Path und Name der CSV-Datei
     * @return Liste der Film-Objekte
     */
    public static List<FilmObjBewertung> importFilmObjFromCsv(String pathFile) throws IOException {
        log.info("importFilmObjFromCsv(): wird ausgeführt.");
        String dateiEndung = ".csv";
        if (!pathFile.substring(pathFile.length() - dateiEndung.length(), pathFile.length()).equals(dateiEndung)) {
            log.error("Datei(" + pathFile + ") ist keine CSV-Datei.");
            return null;
        }
        FilmObjBewertung filmBew;
        List<FilmObjBewertung> bewertungen = new ArrayList<>();
        Path pathToFile = Paths.get(pathFile);
        int zeile = 0;
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (zeile == 0) {
                    //Headerzeile
                    //TODO: ordentliche Ausgabe erstellen
                    log.info("Zeile(" + zeile + "):Header wird eingelesen und besitzt folgende Attribute: " + attributes[0] + ", " + attributes[1] + ", " + attributes[2]);
                } else {
                    log.info("Zeile(" + zeile + "): Film-Bewertung: " + attributes[0] + ", " + attributes[1] + ", " + attributes[2]);
                    filmBew = FilmObjBewertung
                            .builder()
                            .filmUuid(UUID.fromString(String.valueOf(attributes[0]).replace("\"","")))
                            .Gesamtwertung(Integer.valueOf(attributes[1].replace("\"","")))
                            .Zuschauerzahl(Integer.valueOf(attributes[2].replace("\"","")))
                            .build();
                    log.info(filmBew.toString());
                    //TODO: Hinzufügen in der DB scheint Probleme zu machen: Nullpointerexception???
                    bewertungen.add(filmBew);
                }
                line = br.readLine();
                zeile++;
            }
        } catch (IOException ioe) {
            log.error("Datei(" + pathFile + ") konnte nicht importiert werden.");
            throw ioe;
        }
        return bewertungen;
    }


}
