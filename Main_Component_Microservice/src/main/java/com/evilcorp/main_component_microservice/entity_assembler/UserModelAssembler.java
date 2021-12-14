package com.evilcorp.main_component_microservice.entity_assembler;

import com.evilcorp.main_component_microservice.controller.MainUserController;
import com.evilcorp.main_component_microservice.model_classes.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(
                user, //user attribute
                linkTo(methodOn(MainUserController.class).findUser(user.getId())).withSelfRel(),// link zu dem erstellten user
                linkTo(methodOn(MainUserController.class).findAllUsers()).withRel("user"));// link zu allen usern
    }


    public CollectionModel<EntityModel<User>> toCollectionModel(List<? extends User> userEntities) {
        List<EntityModel<User>> users = userEntities.stream().map(user -> this.toModel(user)).collect(Collectors.toList());
        return CollectionModel.of(
                users,
                linkTo(methodOn(MainUserController.class).findAllUsers()).withSelfRel());
    }
}
