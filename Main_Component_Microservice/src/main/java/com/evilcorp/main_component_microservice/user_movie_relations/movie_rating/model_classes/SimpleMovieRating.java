package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SimpleMovieRating {
    private UUID movieId;
    private UUID ownerId;
    private int rating;
}