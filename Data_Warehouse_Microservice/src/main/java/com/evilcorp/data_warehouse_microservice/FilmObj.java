package com.evilcorp.data_warehouse_microservice;

import classes.FilmInformation;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data //Signalisiert eine Datenbank-Data
@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
@Entity //Bestimmt für Spring Boot, dass dieses Objekt eine Tabelle in der Datenbank darstellt.
@Table(name = "Customer") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
public class FilmObj extends FilmInformation {

    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);

    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    public FilmObj() {
        super();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


}
