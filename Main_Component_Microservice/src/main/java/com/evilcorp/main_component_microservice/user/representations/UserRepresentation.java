package com.evilcorp.main_component_microservice.user.representations;

import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserRepresentation extends RepresentationModel<UserRepresentation> {

    private final UUID id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String street;
    private final String street_number;
    private final String postcode;
    private final String placeOfResidence;
    private final List<MovieRenting> rentingList;
    private final List<MovieRating> ratingList;
}
