package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@ToString
public class Movie {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "name",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Getter @Setter
    private UUID id;

    @Column(name = "movie_title", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String title;

}
