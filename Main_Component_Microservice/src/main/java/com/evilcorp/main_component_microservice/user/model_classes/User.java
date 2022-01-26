package com.evilcorp.main_component_microservice.user.model_classes;

import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "users"
)
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@ToString
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "name",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(unique = true)
    @NonNull
    @NotBlank(message = "Username cannot be empty/null")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String username;

    @NonNull
    @NotBlank(message = "Firstname cannot be empty/null")
    @Size(min = 3, max = 15, message = "Firstname must be between 3 and 15 characters!")
    private String firstname;

    @NonNull
    @NotBlank(message = "Lastname cannot be empty/null")
    @Size(min = 3, max = 15, message = "Lastname must be between 3 and 15 characters!")
    private String lastname;

    @Column(unique = true)
    @NonNull
    @NotBlank(message = "Email cannot be empty/null")
    @Email(message = "Email must be valid!")
    private String email;

    @NonNull
    @NotBlank(message = "Street cannot be empty/null")
    @Size(min = 5, max = 40, message = "Streetname must be between 5 and 40 characters!")
    private String street;

    @NonNull
    @NotBlank(message = "Street number cannot be empty/null")
    @Size(max = 5, message = "Streetnumber must be between 0 and 5 characters!")
    private String street_number;

    @NonNull
    @NotBlank(message = "Postcode cannot be empty/null")
    @Size(min = 5, max = 5, message = "Postcode must be exactly 5 characters long!")
    private String postcode;

    @NonNull
    @NotBlank(message = "Place of residence cannot be empty/null")
    @Size(min = 5, max = 40, message = "Place of residence must be between 5 and 40 characters!")
    private String placeOfResidence;

    @OneToMany(mappedBy = "movieRenter", fetch = FetchType.LAZY)
    @Column(name = "rentings")
    @Getter
    @ToString.Exclude
    @JsonManagedReference
    public List<MovieRenting> rentingList = new ArrayList<>();

    @OneToMany(mappedBy = "ratingOwner", fetch = FetchType.LAZY)
    @Column(name = "ratings")
    @Getter
    @ToString.Exclude
    @JsonManagedReference
    public List<MovieRating> ratingList = new ArrayList<>();

    public void update(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.street = user.getStreet();
        this.street_number = user.getStreet_number();
        this.postcode = user.getPostcode();
        this.placeOfResidence = user.getPlaceOfResidence();
    }

    public void addToRentings(MovieRenting newRenting) { this.rentingList.add(newRenting); }

    public boolean removeFromRentings(MovieRenting renting){ return this.rentingList.remove(renting); }

    public void addToRatings(MovieRating newRating) { this.ratingList.add(newRating); }

    public boolean removeFromRatings(MovieRating rating){
        return this.ratingList.remove(rating);
    }

}
