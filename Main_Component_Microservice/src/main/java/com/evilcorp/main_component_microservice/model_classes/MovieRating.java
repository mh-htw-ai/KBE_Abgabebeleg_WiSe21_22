package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;

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
public class MovieRating {

    @Id
    @Column(name = "id", updatable = false)
    @Getter @Setter
    private UUID id = UUID.randomUUID();

    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_owner", nullable = false, columnDefinition = "UUID")
    @Getter @Setter @NonNull
    private User ratingOwner;

    @Column(name = "rating_value", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private int rating;

    @Column(name = "rating_date", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter
    private Date date = new Date();

}
