package com.evilcorp.main_component_microservice.entity_assembler;

import com.evilcorp.main_component_microservice.controller.MainUserController;
import com.evilcorp.main_component_microservice.model_classes.MovieRating;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RatingModelAssembler implements RepresentationModelAssembler<MovieRating, EntityModel<MovieRating>> {

    @Override
    public EntityModel<MovieRating> toModel(MovieRating movieRating) {
        return EntityModel.of(
                movieRating, //user attribute
                linkTo(methodOn(MainUserController.class).findUser(movieRating.getId())).withSelfRel(),//TODO: anpassen an rating conrtoller class link zu dem erstellten user
                linkTo(methodOn(MainUserController.class).findAllUsers()).withRel("user"));// link zu allen usern
    }

    public CollectionModel<EntityModel<MovieRating>> toCollectionModel(List<? extends MovieRating> ratingEntities)  {
        List<EntityModel<MovieRating>> movieRatings = ratingEntities.stream().map(movieRating -> this.toModel(movieRating)).collect(Collectors.toList());
        return CollectionModel.of(
                movieRatings,
                linkTo(methodOn(MainUserController.class).findAllUsers()).withSelfRel());//TODO: anpassen an renting conrtoller class
    }
}
