package com.evilcorp.data_warehouse_microservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmObjBewertung {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @CsvIgnore
    @Getter @Setter
    private long id;

    @CsvBindByName(column = "UUID")
    @Getter @Setter
    private UUID filmUuid;

    @CsvBindByName(column = "Gesamtwertung")
    @Getter @Setter
    private int Gesamtwertung;

    @CsvBindByName(column = "Zuschauerzahl")
    @Getter @Setter
    private int Zuschauerzahl;

    @CsvIgnore @JsonIgnore
    @Getter @Setter
    private LocalDateTime Erstellungsdatum;


    /**
     * Holt die zugehoerigen Headerinformationen fuer die Erstellung eines Headers einer CSV-Datei
     * @return Beispiel: "UUID", "Gesamtwertung", "Zuschauerzahl"
     */
    public static String[] getCsvHeader(){
        return new String[]{"UUID", "Gesamtwertung", "Zuschauerzahl"};
    }


    /**
     * Wandelt das FilmObjBewertung in ein String[] Format um
     * @return String[] mit uuid, GesamtBewertung, Zuschauerzahl
     */
    public String[] convertForCsv(){
        return new String[]{this.getFilmUuid().toString()
                , String.valueOf(this.getGesamtwertung())
                , String.valueOf(this.getZuschauerzahl())};
    }


    /**
     * Erstellt ein Datum für das Datum
     */
    public void createDate(){
        this.setErstellungsdatum(LocalDateTime.now());
    }


    /**
     * Wandelt das FilmObjBewertung in ein String Format um
     * @return String mit uuid, GesamtBewertung, Zuschauerzahl
     */
    public String convertToCsv(){
        return "\"" + this.getFilmUuid() + "\""
                + "," + this.getGesamtwertung()
                + "," + this.getZuschauerzahl();
    }


    @Override
    public String toString(){
        try {
            return "FilmObjBewertung:" + new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
