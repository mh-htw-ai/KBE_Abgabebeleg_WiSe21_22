package com.evilcorp.main_component_microservice.model_representations;

import com.evilcorp.main_component_microservice.model_classes.User;
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
    private final UUID movieID;
    private final User movieRenter;
    private final Date startOfRenting;
}
