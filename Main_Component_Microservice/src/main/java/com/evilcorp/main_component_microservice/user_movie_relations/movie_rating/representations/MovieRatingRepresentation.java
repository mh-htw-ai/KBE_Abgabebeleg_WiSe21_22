package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "rating", collectionRelation = "ratings")
public class MovieRatingRepresentation extends RepresentationModel<MovieRatingRepresentation> {

    private final UUID id;
    private final UUID movieId;
    private final UUID ratingOwnerId;
    private final int rating;
    private final Date ratingDate;
}
