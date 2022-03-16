package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class SimpleMovieRenting {
    private UUID movieId;
    private UUID renterId;
}
