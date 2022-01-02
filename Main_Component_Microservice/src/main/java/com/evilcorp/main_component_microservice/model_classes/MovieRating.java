package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "movie_ratings"/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueMovieAndUserRating", columnNames = {"movie_id","rating_owner"}) //ein user kann einen film nur einmal bewerten
        }*/
)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@ToString
public class MovieRating {

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
    private UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_owner", nullable = false, columnDefinition = "UUID")
    @NonNull
    @ToString.Exclude
    private User ratingOwner;

    @Column(name = "rating_value", nullable = false, columnDefinition = "TEXT")
    private int rating;

    @Column(name = "rating_date", nullable = false, columnDefinition = "TEXT")
    private Date ratingDate = new Date();

}
