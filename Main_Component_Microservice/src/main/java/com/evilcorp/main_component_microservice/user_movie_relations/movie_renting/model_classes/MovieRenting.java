package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes;

import com.evilcorp.main_component_microservice.user.model_classes.User;
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
    private UUID id;

    @NonNull
    private UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_renter", nullable = false, columnDefinition = "UUID")
    @NonNull
    @ToString.Exclude
    private User movieRenter;

    private Date startOfRenting = new Date();

}
