package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @NonNull
    private UUID renterId;

    private Date startOfRenting = new Date();
}
