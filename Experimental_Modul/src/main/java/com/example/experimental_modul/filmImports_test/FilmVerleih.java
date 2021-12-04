package com.example.experimental_modul.filmImports_test;


import classes.Film;

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