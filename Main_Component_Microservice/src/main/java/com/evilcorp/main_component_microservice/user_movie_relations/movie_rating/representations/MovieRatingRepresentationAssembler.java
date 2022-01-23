package com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations;

import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.controllers.MainRatingController;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.representations.MovieRatingRepresentation;
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

        ratingRepresentation.add( linkTo( methodOn(MainRatingController.class).getMovieRating( ratingRepresentation.getId() ) ).withSelfRel() );

        return ratingRepresentation;
    }

    @Override
    public CollectionModel<MovieRatingRepresentation> toCollectionModel(Iterable<? extends MovieRating> entities) {
        CollectionModel<MovieRatingRepresentation> ratingRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);

        ratingRepresentations.add( linkTo( methodOn(MainRatingController.class).getAllMovieRatings() ).withSelfRel() );

        return ratingRepresentations;
    }
}
