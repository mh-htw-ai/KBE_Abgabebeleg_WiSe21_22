package com.evilcorp.main_component_microservice.model_classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.UUID;

//@Entity
public class MovieRating {

   // @Id
    private UUID id;
   // @ManyToOne
    private Movie ratedMovie;
  //  @OneToOne
    private User ratingOwner;
    private int rating;
    private Date date;

    public MovieRating() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Movie getRatedMovie() {
        return ratedMovie;
    }

    public void setRatedMovie(Movie ratedMovie) {
        this.ratedMovie = ratedMovie;
    }

    public User getRatingOwner() {
        return ratingOwner;
    }

    public void setRatingOwner(User ratingOwner) {
        this.ratingOwner = ratingOwner;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
