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
@ToString
public class MovieRating {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "name",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Getter @Setter
    private UUID id;

    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_owner", nullable = false, columnDefinition = "UUID")
    @Getter @Setter @NonNull
    @ToString.Exclude
    private User ratingOwner;

    @Column(name = "rating_value", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter
    private int rating;

    @Column(name = "rating_date", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter
    private Date date = new Date();

}
