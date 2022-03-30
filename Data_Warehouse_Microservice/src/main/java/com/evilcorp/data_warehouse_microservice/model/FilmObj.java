package com.evilcorp.data_warehouse_microservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "FilmObj") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
@Table
public class FilmObj implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);

    @Id
    @Getter
    @Setter
    @Column(unique = true)
    //@Column(name = "id", unique = true)
    private UUID id;

    @Getter
    @Setter
    @Column
    //@Column(name = "titel")
    private String titel;

    @Getter
    @Setter
    @Column
    //@Column(name = "leihPreis")
    private double leihPreis;

    @JsonIgnore
    @Getter
    @Setter
    @Column
    //@Column(name = "geloescht")
    private boolean geloescht = false;

    @Getter
    @Setter
    //@Column(name = "kurzbeschreibung")
    @Column
    private String kurzbeschreibung ;

    //@JsonIgnore
    @Getter
    @Setter
    @Column(length = 65535, columnDefinition = "text")
    //@Column(name = "beschreibung",length = 65535, columnDefinition = "text")
    private String beschreibung;

    @Override
    public String toString(){
        //return "FilmObj: " + "uuid_film=" + this.uuid_film +"titel=" + this.titel + "leihpreis=" + this.leihPreis;
        try {
            return "FilmObj:" + new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
