package com.evilcorp.main_component_microservice.model_classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

//@Entity
public class Movie {

    //@Id
    private UUID uuid_Film;
    private String title;

    public Movie() {

    }

    public UUID getUuid_Film() {
        return uuid_Film;
    }

    public void setUuid_Film(UUID uuid_Film) {
        this.uuid_Film = uuid_Film;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
