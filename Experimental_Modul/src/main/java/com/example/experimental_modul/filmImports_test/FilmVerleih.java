package com.example.experimental_modul.filmImports_test;


import classes.Film;
import lombok.*;
import org.hibernate.internal.build.AllowPrintStacktrace;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;


public class FilmVerleih extends Film {
    private Date tag;

    public FilmVerleih(UUID uuid_Film, Date tag) {
        super(uuid_Film);
        this.tag = tag;
    }

    public Date getTag() {
        return tag;
    }

    public void setTag(Date tag) {
        this.tag = tag;
    }
}

/*
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FilmVerleih  {

    @Getter @Setter @Id
    private UUID uuid_Film;

    @Getter @Setter
    private String titel;

    @Getter @Setter
    private int spielzeitInMinuten;

    @Getter @Setter
    private Date tag;


}
*/