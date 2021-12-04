package com.example.experimental_modul.filmImports_test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
