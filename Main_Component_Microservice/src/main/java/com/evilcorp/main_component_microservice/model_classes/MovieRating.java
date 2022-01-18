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
    private UUID id;

    @NonNull
    private UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_owner", nullable = false, columnDefinition = "UUID")
    @NonNull
    @ToString.Exclude
    private User ratingOwner;

    private int rating;

    private Date ratingDate = new Date();

}
