package com.example.experimental_modul.customer_test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Data //Signalisiert eine Datenbank-Data
@Builder //Erstellt automatisch einen Builder mit saemtlichen Attributen des Objektes
@NoArgsConstructor //Erstellt automatisch einen Konstruktor mit keinem Parameter
@AllArgsConstructor //Erstellt automatisch einen Konstruktor mit sämtlichen Parametern
@Entity //Bestimmt für Spring Boot, dass dieses Objekt eine Tabelle in der Datenbank darstellt.
@Table(name = "Customer") // Legt fest wie die Tabelle in der Datenbank benannt werden soll.
public class Customer {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

}
