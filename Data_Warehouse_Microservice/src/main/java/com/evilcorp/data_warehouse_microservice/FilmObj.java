package com.evilcorp.data_warehouse_microservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data //Signalisiert eine Datenbank-Data
@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FilmObj") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
public class FilmObj implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(com.evilcorp.data_warehouse_microservice.FilmObj.class);

    @Id
    @Getter
    @Setter
    private UUID uuid_film ;
    @Getter
    @Setter
    private String titel;
    @Getter
    @Setter
    private double leihPreis;
    @JsonIgnore
    @Getter
    @Setter
    private boolean geloescht = false;

    @Override
    public String toString(){
        //return "FilmObj: " + "uuid_film=" + this.uuid_film +"titel=" + this.titel + "leihpreis=" + this.leihPreis;
        try {
            return "FilmObj" + new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}

    /*
@Data //Signalisiert eine Datenbank-Data
@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
//@SuperBuilder
//@Entity //Bestimmt für Spring Boot, dass dieses Objekt eine Tabelle in der Datenbank darstellt.
@Table(name = "Film") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
//@DiscriminatorValue("FilmObj")
public class FilmObj extends FilmInformation {
    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);

    //TODO: Quelle für Vererbung in Jar-Bib mit Spring Boot lesen: https://www.spring-boot-blog.de/blog/jpa-inheritance/

    public FilmObj() {
        super();

    }

 */
