package com.evilcorp.main_component_microservice.model_classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "users"
)
public class User {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "username", nullable = false, columnDefinition = "TEXT", unique = true)
    private String username;
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstname;
    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastname;
    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;
    @Column(name = "street", nullable = false, columnDefinition = "TEXT")
    private String street;
    @Column(name = "street_number", nullable = false, columnDefinition = "TEXT")
    private String street_number;
    @Column(name = "postcode", nullable = false, columnDefinition = "TEXT")
    private String postcode;
    @Column(name = "place_of_residence", nullable = false, columnDefinition = "TEXT")
    private String placeOfResidence;

    @OneToMany(mappedBy = "movieRenter", fetch = FetchType.LAZY)
    @Column(name = "rentings")
    public List<MovieRenting> rentingList = new ArrayList<>();
    @OneToMany(mappedBy = "ratingOwner", fetch = FetchType.LAZY)
    @Column(name = "ratings")
    public List<MovieRating> ratingList = new ArrayList<>();

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


    /*
    public boolean addMovieRenting(MovieRenting movieRenting){
        return this.rentingList.add(movieRenting);
    }

    public boolean containsMovieRenting(MovieRenting movieRenting){
        return this.rentingList.contains(movieRenting);
    }

    public boolean removeMovieRenting(MovieRenting movieRenting){
        return this.rentingList.remove(movieRenting);
    }

    public boolean addMovieRating(MovieRating movieRating){
        return this.ratingList.add(movieRating);
    }

    public boolean removeMovieRating(MovieRating movieRating){
        return this.ratingList.remove(movieRating);
    }*/

}
