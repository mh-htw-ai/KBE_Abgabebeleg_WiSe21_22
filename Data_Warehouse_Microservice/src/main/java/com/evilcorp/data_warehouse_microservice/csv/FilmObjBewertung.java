package com.evilcorp.data_warehouse_microservice.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class FilmObjBewertung {

    @Getter @Setter @CsvBindByName(column = "UUID")
    private UUID uuid;

    @Getter @Setter @CsvBindByName(column = "Gesamtwertung")
    private int Gesamtwertung;

    @Getter @Setter @CsvBindByName(column = "Zuschauerzahl")
    private int Zuschauerzahl;

    /**
     * Holt die zugehoerigen Headerinformationen fuer die Erstellung eines Headers einer CSV-Datei
     * @return Beispiel: "UUID", "Gesamtwertung", "Zuschauerzahl"
     */
    public static String[] getCsvHeader(){
        String[] ausgabe = {"UUID", "Gesamtwertung", "Zuschauerzahl"};
        return ausgabe;
    }

    /**
     * Wandelt das FilmObjBewertung in ein String[] Format um
     * @return String[] mit uuid, GesamtBewertung, Zuschauerzahl
     */
    public String[] convertForCsv(){
        String[] ausgabe = {this.getUuid().toString()
                , String.valueOf(this.getGesamtwertung())
                , String.valueOf(this.getZuschauerzahl())};
        return ausgabe;
    }

    /**
     * Wandelt das FilmObjBewertung in ein String Format um
     * @return String mit uuid, GesamtBewertung, Zuschauerzahl
     */
    public String convertToCsv(){
        return "\"" + this.getUuid() + "\""
                + "," + this.getGesamtwertung()
                + "," + this.getZuschauerzahl();
    }
}
