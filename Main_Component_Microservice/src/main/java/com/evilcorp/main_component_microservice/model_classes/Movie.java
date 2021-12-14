package com.evilcorp.main_component_microservice.model_classes;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "movies",
        uniqueConstraints = {
                @UniqueConstraint(name = "movie_title_unique", columnNames = "movie_title")
        }
)
public class Movie {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "movie_title", nullable = false, columnDefinition = "TEXT")
    private String title;

    public Movie() {

    }

    public Movie(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid_Film) {
        this.id = uuid_Film;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
