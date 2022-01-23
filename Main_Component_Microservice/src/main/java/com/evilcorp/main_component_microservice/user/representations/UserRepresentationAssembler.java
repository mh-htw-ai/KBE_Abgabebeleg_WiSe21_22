package com.evilcorp.main_component_microservice.user.representations;

import com.evilcorp.main_component_microservice.user.controllers.MainUserController;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.representations.UserRepresentation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRepresentationAssembler implements RepresentationModelAssembler<User, UserRepresentation> {

    @Override
    public UserRepresentation toModel(User entity) {
        UserRepresentation userRepresentation = UserRepresentation.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .street(entity.getStreet())
                .street_number(entity.getStreet_number())
                .postcode(entity.getPostcode())
                .placeOfResidence(entity.getPlaceOfResidence())
                .build();

        userRepresentation.add( linkTo( methodOn(MainUserController.class).getUser( userRepresentation.getId() ) ).withSelfRel() );
        userRepresentation.add( linkTo( methodOn(MainUserController.class).deleteUser( userRepresentation.getId() ) ).withRel("delete") );//sollte man einen delete link mit zurueckgeben?

        return userRepresentation;
    }

    @Override
    public CollectionModel<UserRepresentation> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserRepresentation> userRepresentations = RepresentationModelAssembler.super.toCollectionModel(entities);

        userRepresentations.add( linkTo( methodOn(MainUserController.class).getAllUsers() ).withSelfRel() );

        return userRepresentations;
    }
}