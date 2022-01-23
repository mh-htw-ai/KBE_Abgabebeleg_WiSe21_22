package com.evilcorp.main_component_microservice.entity_assembler;

import com.evilcorp.main_component_microservice.controller.MainRentingController;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.model_representations.MovieRentingRepresentation;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieRentingRepresentationAssembler implements RepresentationModelAssembler<MovieRenting, MovieRentingRepresentation> {

    @Override
    public @NonNull MovieRentingRepresentation toModel(MovieRenting entity) {
        MovieRentingRepresentation rentingRepresentation = MovieRentingRepresentation.builder()
                .id(entity.getId())
                .movieID(entity.getMovieId())
                .movieRenter(entity.getMovieRenter())
                .startOfRenting(entity.getStartOfRenting())
                .build();

        rentingRepresentation.add( linkTo( methodOn(MainRentingController.class).getMovieRenting( rentingRepresentation.getId() ) ).withSelfRel() );

        return rentingRepresentation;
    }

    @Override
    public @NonNull CollectionModel<MovieRentingRepresentation> toCollectionModel(Iterable<? extends MovieRenting> entities) {
        CollectionModel<MovieRentingRepresentation> rentingRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);

        rentingRepresentations.add( linkTo( methodOn(MainRentingController.class).getAllMovieRentings() ).withSelfRel() );

        return rentingRepresentations;
    }
}
