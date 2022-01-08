package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@ToString
@Getter @Setter
public class MovieRenting {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "name",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    @NonNull
    private UUID movieID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_renter", nullable = false, columnDefinition = "UUID")
    @NonNull
    @ToString.Exclude
    private User movieRenter;

    @Column(name = "date_of_renting", nullable = false, columnDefinition = "TEXT")
    private Date startOfRenting = new Date();

}