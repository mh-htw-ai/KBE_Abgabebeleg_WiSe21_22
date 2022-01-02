package com.evilcorp.main_component_microservice.entity_assembler;

import com.evilcorp.main_component_microservice.controller.MainRatingController;
import com.evilcorp.main_component_microservice.controller.MainUserController;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.model_representations.MovieRatingRepresentation;
import com.evilcorp.main_component_microservice.model_representations.UserRepresentation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieRatingRepresentationAssembler implements RepresentationModelAssembler<MovieRating, MovieRatingRepresentation> {


    @Override
    public MovieRatingRepresentation toModel(MovieRating entity) {
        MovieRatingRepresentation ratingRepresentation = MovieRatingRepresentation.builder()
                .id(entity.getId())
                .movieId(entity.getMovieId())
                .ratingOwner(entity.getRatingOwner())
                .rating(entity.getRating())
                .ratingDate(entity.getRatingDate())
                .build();

        ratingRepresentation.add( linkTo( methodOn(MainRatingController.class).findMovieRating( ratingRepresentation.getId() ) ).withSelfRel() );

        return ratingRepresentation;
    }

    @Override
    public CollectionModel<MovieRatingRepresentation> toCollectionModel(Iterable<? extends MovieRating> entities) {
        CollectionModel<MovieRatingRepresentation> ratingRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);

        ratingRepresentations.add( linkTo( methodOn(MainRatingController.class).findAllMovieRatings() ).withSelfRel() );

        return ratingRepresentations;
    }
}
