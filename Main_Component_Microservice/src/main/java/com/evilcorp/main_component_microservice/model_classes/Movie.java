package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;



@Entity
@Table(
        name = "movies",
        uniqueConstraints = {
                @UniqueConstraint(name = "movie_title_unique", columnNames = "movie_title")
        }
)
@NoArgsConstructor
@RequiredArgsConstructor
public class Movie {

    @Id
    @Column(name = "id", updatable = false)
    @Getter @Setter
    private UUID id = UUID.randomUUID();

    @Column(name = "movie_title", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String title;

}
