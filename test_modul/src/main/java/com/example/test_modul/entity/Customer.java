package com.example.test_modul.entity;



import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
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
