package com.evilcorp.main_component_microservice.model_classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique",columnNames = "email")
        }
)
public class User {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    private String username;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstname;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastname;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;
    @Column(name = "street", nullable = false, columnDefinition = "TEXT")
    private String street;
    @Column(name = "street_number", nullable = false, columnDefinition = "TEXT")
    private String street_number;
    @Column(name = "postcode", nullable = false, columnDefinition = "TEXT")
    private String postcode;
    @Column(name = "place_of_residence", nullable = false, columnDefinition = "TEXT")
    private String placeOfResidence;
/*
    @ManyToMany
    @Column(name = "rented_movies")
    private ArrayList<Movie> rentedMovies = new ArrayList<Movie>();
    @OneToMany
    @Column(name = "movie_ratings")
    private ArrayList<MovieRating> movieRatings = new ArrayList<MovieRating>();
*/
    public User() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }
}
