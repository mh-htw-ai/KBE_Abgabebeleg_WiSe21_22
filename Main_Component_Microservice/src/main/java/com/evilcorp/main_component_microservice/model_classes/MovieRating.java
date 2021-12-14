package com.evilcorp.main_component_microservice.model_classes;

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
public class MovieRating {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    private UUID movieId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rating_owner", nullable = false, columnDefinition = "UUID")
    private User ratingOwner;
    @Column(name = "rating_value", nullable = false, columnDefinition = "TEXT")
    private int rating;
    @Column(name = "rating_date", nullable = false, columnDefinition = "TEXT")
    private Date date;

    public MovieRating() {

    }

    public MovieRating(UUID movieId, User ratingOwner, int rating) {
        this.id = UUID.randomUUID();
        this.movieId = movieId;
        this.ratingOwner = ratingOwner;
        this.rating = rating;
        this.date = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
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
