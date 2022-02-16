package com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations;

import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.controllers.MainRentingController;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.representations.MovieRentingRepresentation;
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
                .movieId(entity.getMovieId())
                .movieRenterId(entity.getMovieRenter().getId())
                .startOfRenting(entity.getStartOfRenting())
                .build();

        rentingRepresentation.add( linkTo( methodOn(MainRentingController.class).getMovieRenting( rentingRepresentation.getId().toString() ) ).withSelfRel() );

        return rentingRepresentation;
    }

    @Override
    public @NonNull CollectionModel<MovieRentingRepresentation> toCollectionModel(@NonNull Iterable<? extends MovieRenting> entities) {
        CollectionModel<MovieRentingRepresentation> rentingRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);

        rentingRepresentations.add( linkTo( methodOn(MainRentingController.class).getAllMovieRentings() ).withSelfRel() );

        return rentingRepresentations;
    }
}
