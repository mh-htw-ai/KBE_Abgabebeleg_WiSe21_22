package com.evilcorp.data_warehouse_microservice.logic;

import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CsvImporterServiceTest {

    String testdateiName = "testDateiFilmBewertung.csv";
    String path = new File("").getAbsoluteFile().toString();
    String csvHeader = "\"UUID\",\"Gesamtwertung\",\"Zuschauerzahl\"";
    List<FilmObjBewertung> testListe;
    String aktPath = "";


    @BeforeEach
    void setUp() {
        testListe = new ArrayList<>();
        testListe.add(FilmObjBewertung.builder()
                .filmUuid(UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117"))
                .Gesamtwertung(44)
                .Zuschauerzahl(22)
                .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                .build());
        testListe.add(FilmObjBewertung.builder()
                .filmUuid(UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117"))
                .Gesamtwertung(77)
                .Zuschauerzahl(37)
                .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                .build());
        testListe.add(FilmObjBewertung.builder()
                .filmUuid(UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212"))
                .Gesamtwertung(77)
                .Zuschauerzahl(33)
                .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                .build());
        testListe.add(FilmObjBewertung.builder()
                .filmUuid(UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212"))
                .Gesamtwertung(32)
                .Zuschauerzahl(16)
                .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                .build());
    }


    @AfterEach
    void tearDown() {
        deleteDatei(aktPath);
    }


    @Test
    void importFilmObjFromCsvNoFile() throws IOException {
        deleteDatei(path + testdateiName);
        File file = new File(path + testdateiName);
        if (file.exists()) {
            fail("Datei existiert bereits.");
        }
        boolean erg = false;
        try {
            CsvImporterService.importFilmObjFromCsv(path + testdateiName);
        } catch (FileNotFoundException ex) {
            erg = true;
        }
        if (!erg) {
            fail("Es konnte eine nicht existierende File importiert werden.");
        }
    }


    @Test
    void importFilmObjFromCsvWrongFileType() throws IOException {
        String eintraege = getCsvZeileFromFilmObj(testListe.get(0)) + System.getProperty("line.separator");
        aktPath = writeTestDatei(path, "falscherDateityp.cv", eintraege);
        boolean erg = false;
        try {
            CsvImporterService.importFilmObjFromCsv(aktPath);
        } catch (UnsupportedEncodingException ex) {
            erg = true;
        }
        if (!erg) {
            fail("Datei konnte trotz fehlerhaften Format akzeptiert.");
        }
    }


    @Test
    void importFilmObjFromCsvOnePcsSucced() throws IOException {
        String eintraege = getCsvZeileFromFilmObj(testListe.get(0)) + System.getProperty("line.separator");
        aktPath = writeTestDatei(path, testdateiName, eintraege);
        List<FilmObjBewertung> _aktBewertungsliste;
        _aktBewertungsliste = CsvImporterService.importFilmObjFromCsv(aktPath);
        assertNotNull(_aktBewertungsliste);
        assertEquals(1, _aktBewertungsliste.size());
        assertTrue(checkAttributeFilmObjBew(testListe.get(0), _aktBewertungsliste.get(0)));
    }


    @Test
    void importFilmObjFromCsvFourPcsSucced() throws IOException {
        String eintraege = getCsvZeileFromFilmObj(testListe.get(0)) + System.getProperty("line.separator")
                + getCsvZeileFromFilmObj(testListe.get(1)) + System.getProperty("line.separator")
                + getCsvZeileFromFilmObj(testListe.get(2)) + System.getProperty("line.separator")
                + getCsvZeileFromFilmObj(testListe.get(3)) + System.getProperty("line.separator");
        aktPath = writeTestDatei(path, testdateiName, eintraege);
        List<FilmObjBewertung> _aktBewertungsliste;
        _aktBewertungsliste = CsvImporterService.importFilmObjFromCsv(aktPath);
        assertNotNull(_aktBewertungsliste);
        assertEquals(4, _aktBewertungsliste.size());
        assertTrue(checkAttributeFilmObjBew(testListe.get(0), _aktBewertungsliste.get(0)));
        assertTrue(checkAttributeFilmObjBew(testListe.get(1), _aktBewertungsliste.get(1)));
        assertTrue(checkAttributeFilmObjBew(testListe.get(2), _aktBewertungsliste.get(2)));
        assertTrue(checkAttributeFilmObjBew(testListe.get(3), _aktBewertungsliste.get(3)));
    }


    @Test
    void importFilmObjFromWrongCsvFileWithoutHeader() throws IOException {
        String eintraege = getCsvZeileFromFilmObj(testListe.get(0)) + System.getProperty("line.separator");
        aktPath = writeTestDatei(path, testdateiName, eintraege, false);
        boolean erg = false;
        try {
            CsvImporterService.importFilmObjFromCsv(aktPath);
        } catch (UnsupportedEncodingException ex) {
            erg = true;
        }
        if(!erg){
            fail("CSV-Datei ohne Header wurde dennoch akzeptiert.");
        }
    }


    @Test
    void importFilmObjFromWrongCsvFileWithWrongHeader() throws IOException {
        String manipulierterHeader = csvHeader + ",\"Zuschauerzahl\"";
        String eintraege = manipulierterHeader + getCsvZeileFromFilmObj(testListe.get(0)) + System.getProperty("line.separator");
        aktPath = writeTestDatei(path, testdateiName, eintraege, false);
        boolean erg = false;
        try {
            CsvImporterService.importFilmObjFromCsv(aktPath);
        } catch (UnsupportedEncodingException ex) {
            erg = true;
        }
        if(!erg){
            fail("CSV-Datei ohne Header wurde dennoch akzeptiert.");
        }
    }

    private String writeTestDatei(String filePath, String fileName, String eintraege) {
        return writeTestDatei(filePath, fileName, eintraege, true);
    }

    private String writeTestDatei(String filePath, String fileName, String eintraege, Boolean withHeader) {
        String testData = "";
        if (withHeader) {
            testData += csvHeader + System.getProperty("line.separator");
        }
        testData += eintraege;
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new FileWriter(filePath + fileName));
            pWriter.print(testData);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (pWriter != null) {
                pWriter.flush();
                pWriter.close();
            }
        }
        return filePath + fileName;
    }

    private String getCsvZeileFromFilmObj(FilmObjBewertung bew) {
        return "\"" + bew.getFilmUuid().toString() + "\",\"" + bew.getGesamtwertung() + "\",\"" + bew.getZuschauerzahl() + "\"";
    }

    private boolean checkAttributeFilmObjBew(FilmObjBewertung exp, FilmObjBewertung act) {
        if (!exp.getFilmUuid().toString().equals(act.getFilmUuid().toString())) {
            return false;
        }
        if (exp.getZuschauerzahl() != act.getZuschauerzahl()) {
            return false;
        }
        return exp.getGesamtwertung() == act.getGesamtwertung();
    }

    private void deleteDatei(String _path) {
        File file = new File(_path);
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }
}