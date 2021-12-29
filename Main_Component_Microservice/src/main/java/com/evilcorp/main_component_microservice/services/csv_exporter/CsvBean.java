package com.evilcorp.main_component_microservice.services.csv_exporter;

import java.util.UUID;

public class CsvBean {

    private UUID movieId;
    private int ratingUserCount;
    private int averageRating;

    public CsvBean() {
    }

    public CsvBean(UUID movieId, int ratingUserCount, int averageRating) {
        this.movieId = movieId;
        this.ratingUserCount = ratingUserCount;
        this.averageRating = averageRating;
    }

    public UUID getMovieId() {
        return movieId;
    }

    public void setMovieId(UUID movieId) {
        this.movieId = movieId;
    }

    public int getRatingUserCount() {
        return ratingUserCount;
    }

    public void setRatingUserCount(int ratingUserCount) {
        this.ratingUserCount = ratingUserCount;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

}
