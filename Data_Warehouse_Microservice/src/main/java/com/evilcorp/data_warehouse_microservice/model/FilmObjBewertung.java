package com.evilcorp.data_warehouse_microservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FilmObjBewertung")
public class FilmObjBewertung {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter @Column(name="id", unique = true) @CsvIgnore
    private long id;

    @Getter @Setter @Column(name = "filmUuid") @CsvBindByName(column = "UUID")
    private UUID filmUuid;

    @Getter @Setter @Column(name="Gesamtwertung") @CsvBindByName(column = "Gesamtwertung")
    private int Gesamtwertung;

    @Getter @Setter @Column(name="Zuschauerzahl") @CsvBindByName(column = "Zuschauerzahl")
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
        String[] ausgabe = {this.getFilmUuid().toString()
                , String.valueOf(this.getGesamtwertung())
                , String.valueOf(this.getZuschauerzahl())};
        return ausgabe;
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
