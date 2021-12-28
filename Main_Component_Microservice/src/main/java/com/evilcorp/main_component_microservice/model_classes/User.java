package com.evilcorp.main_component_microservice.model_classes;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "users"
)
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @Column(name = "id", updatable = false)
    @Getter @Setter
    private UUID id = UUID.randomUUID();

    @Column(name = "username", nullable = false, columnDefinition = "TEXT", unique = true)
    @Getter @Setter @NonNull
    private String username;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String firstname;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String lastname;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    @Getter @Setter @NonNull
    private String email;

    @Column(name = "street", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String street;

    @Column(name = "street_number", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String street_number;

    @Column(name = "postcode", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String postcode;

    @Column(name = "place_of_residence", nullable = false, columnDefinition = "TEXT")
    @Getter @Setter @NonNull
    private String placeOfResidence;

    @OneToMany(mappedBy = "movieRenter", fetch = FetchType.LAZY)
    @Column(name = "rentings")
    public List<MovieRenting> rentingList = new ArrayList<>();
    @OneToMany(mappedBy = "ratingOwner", fetch = FetchType.LAZY)
    @Column(name = "ratings")
    public List<MovieRating> ratingList = new ArrayList<>();

}
