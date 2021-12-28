package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "movie_rentings"/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueMovieAndUserRenting", columnNames = {"movie_id","movie_renter"})//ein user kann einen film nur ein mal zur selben zeit ausleihen
        }*/
)
@NoArgsConstructor
@RequiredArgsConstructor
public class MovieRenting {

    @Id
    @Column(name = "id", updatable = false)
    @Getter @Setter
    private UUID id = UUID.randomUUID();

    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private UUID movieID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_renter", nullable = false, columnDefinition = "UUID")
    @Getter @Setter @NonNull
    private User movieRenter;

    @Column(name = "date_of_renting", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter
    private Date startOfRenting = new Date();

}
