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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class FilmObj implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);

    @Id
    @Getter
    @Setter
    @Column(unique = true)
    private UUID id;

    @Getter
    @Setter
    @Column
    private String titel;

    @Getter
    @Setter
    @Column
    private double leihPreis;

    @JsonIgnore
    @Getter
    @Setter
    @Column
    private boolean geloescht = false;

    @Getter
    @Setter
    @Column
    private String kurzbeschreibung ;

    @Getter
    @Setter
    @Column(length = 65535, columnDefinition = "text")
    private String beschreibung;

    @Override
    public String toString(){
        try {
            return "FilmObj:" + new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
