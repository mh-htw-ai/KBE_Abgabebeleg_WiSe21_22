package com.evilcorp.main_component_microservice.model_classes;

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
public class MovieRenting {

    @Id
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "movie_id", nullable = false, columnDefinition = "TEXT")
    private UUID movieID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_renter", nullable = false, columnDefinition = "UUID")
    private User movieRenter;
    @Column(name = "date_of_renting", nullable = false, columnDefinition = "TEXT")
    private Date startOfRenting;

    public MovieRenting() {
    }

    public MovieRenting(UUID movieID, User movieRenter) {
        this.id = UUID.randomUUID();
        this.movieID = movieID;
        this.movieRenter = movieRenter;
        this.startOfRenting = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMovieID() {
        return movieID;
    }

    public void setMovieID(UUID movieID) {
        this.movieID = movieID;
    }

    public User getMovieRenter() {
        return movieRenter;
    }

    public void setMovieRenter(User movieRenter) {
        this.movieRenter = movieRenter;
    }

    public Date getStartOfRenting() {
        return startOfRenting;
    }

    public void setStartOfRenting(Date startOfRenting) {
        this.startOfRenting = startOfRenting;
    }
}
