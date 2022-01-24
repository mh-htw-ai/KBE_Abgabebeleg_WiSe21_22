package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations;

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
@Relation(itemRelation = "renting", collectionRelation = "rentings")
public class MovieRentingRepresentation extends RepresentationModel<MovieRentingRepresentation> {

    private final UUID id;
    private final UUID movieId;
    private final UUID movieRenterId;
    private final Date startOfRenting;
}
