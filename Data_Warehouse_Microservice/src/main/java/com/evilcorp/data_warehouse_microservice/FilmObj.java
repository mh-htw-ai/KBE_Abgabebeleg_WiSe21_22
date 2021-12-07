package com.evilcorp.data_warehouse_microservice;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data //Signalisiert eine Datenbank-Data
@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FilmObj") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
public class FilmObj {

    private static final Logger log = LoggerFactory.getLogger(com.evilcorp.data_warehouse_microservice.FilmObj.class);
    @Id
    @Getter
    @Setter
    private UUID uuid_film = UUID.randomUUID();
    @Getter
    @Setter
    private String titel;
    @Getter
    @Setter
    private double leihPreis;


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

    //TODO: Quelle lesen: https://www.spring-boot-blog.de/blog/jpa-inheritance/

    public FilmObj() {
        super();

    }

 */
