package com.example.test_modul.filmImports_test;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BenutzerTest")
public class BenutzerTEST {

    @Id
    @Column(name="uuid")
    private UUID uuid;

    //@OneToMany
    //private List<> ausleihListe

    private UUID film_uuid;

    private String name;

}
