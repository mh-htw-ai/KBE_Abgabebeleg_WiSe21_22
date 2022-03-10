package com.evilcorp.main_component_microservice.user.services;

import com.evilcorp.main_component_microservice.user.controllers.MainUserController;
import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import com.evilcorp.main_component_microservice.user.representations.UserRepresentation;
import com.evilcorp.main_component_microservice.user.representations.UserRepresentationAssembler;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_rating.model_classes.MovieRating;
import com.evilcorp.main_component_microservice.user_movie_relations.movie_renting.model_classes.MovieRenting;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRepresentationAssembler userAssembler;

    public UserRepresentation getUser(UUID userId){
        User user = this.getUserByRepo(userId);
        return userAssembler
                .toModel(user)
                .add( linkTo( methodOn(MainUserController.class).getAllUsers() ).withRel("users"));
    }

    public CollectionModel<UserRepresentation> getAllUsers(){
        List<User> users = userRepository.findAll();
        return userAssembler.toCollectionModel(users);
    }

    public Link createUser(User newUser){
        User tempUser;
        try {
            tempUser = userRepository.save(newUser);
        }catch(Exception e){
            throw new UserAlreadyExistsException(newUser.getId());
        }
        return linkTo( methodOn(MainUserController.class).getUser( tempUser.getId().toString() ) ).withSelfRel();
    }

    public Link updateUser(User updateUser){
        User existingUser = this.getUserByRepo(updateUser.getId());
        existingUser.update(updateUser);
        userRepository.save(existingUser);
        return linkTo( methodOn(MainUserController.class).getUser( existingUser.getId().toString() ) ).withSelfRel();
    }

    public void deleteUser(UUID userId){
        User userToBeDeleted = this.getUserByRepo(userId);
        userRepository.delete(userToBeDeleted);
    }

    public boolean checkIfCorrespondingUserExists(UUID userId){
        User correspondingUser = this.getUserByRepo(userId);
        return correspondingUser != null;
    }

    public User getUserByRepo(UUID userId){
        Optional<User> correspondingUserContainer = userRepository.findById(userId);
        return this.unwrapUserContainer(correspondingUserContainer, userId);
    }

    private User unwrapUserContainer(Optional<User> userContainer, UUID userId){
        User user;
        if(userContainer.isPresent()){
            user = userContainer.get();
        }else{
            throw new UserNotFoundException(userId);
        }
        return user;
    }
}