package com.evilcorp.data_warehouse_microservice;

import classes.FilmInformation;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data //Signalisiert eine Datenbank-Data
//@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
@SuperBuilder
//@Entity //Bestimmt für Spring Boot, dass dieses Objekt eine Tabelle in der Datenbank darstellt.
//@Table(name = "Film") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
@DiscriminatorValue("FilmObj")
public class FilmObj extends FilmInformation {
    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);

    //TODO: Quelle lesen: https://www.spring-boot-blog.de/blog/jpa-inheritance/

    public FilmObj() {
        super();

    }




}
