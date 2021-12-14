package com.evilcorp.main_component_microservice.entity_assembler;

import com.evilcorp.main_component_microservice.controller.MainUserController;
import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RentingModelAssembler implements RepresentationModelAssembler<MovieRenting, EntityModel<MovieRenting>> {

    @Override
    public EntityModel<MovieRenting> toModel(MovieRenting movieRenting) {
        return EntityModel.of(
                movieRenting, //user attribute
                linkTo(methodOn(MainUserController.class).findUser(movieRenting.getId())).withSelfRel(),// TODO: anpassen an renting conrtoller class link zu dem erstellten user
                linkTo(methodOn(MainUserController.class).findAllUsers()).withRel("renting"));// link zu allen usern
    }

    public CollectionModel<EntityModel<MovieRenting>> toCollectionModel(List<? extends MovieRenting> rentingEntities)  {
        List<EntityModel<MovieRenting>> movieRentings = rentingEntities.stream().map(movieRenting -> this.toModel(movieRenting)).collect(Collectors.toList());
        return CollectionModel.of(
                movieRentings,
                linkTo(methodOn(MainUserController.class).findAllUsers()).withSelfRel());//TODO: anpassen an renting conrtoller class
    }
}
