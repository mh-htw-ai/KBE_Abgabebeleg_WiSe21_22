package com.evilcorp.main_component_microservice.user.services;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import com.evilcorp.main_component_microservice.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(UUID userId){
        return this.getUserByRepo(userId);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UUID createUser(User newUser){
        User tempUser;
        try {
            tempUser = userRepository.save(newUser);
        }catch(Exception e){
            throw new UserAlreadyExistsException(newUser.getId());
        }
        return tempUser.getId();
    }

    public User updateUser(User updateUser){
        User existingUser = this.getUserByRepo(updateUser.getId());
        existingUser.update(updateUser);
        userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUser(UUID userId){
        User userToBeDeleted = this.getUserByRepo(userId);
        userRepository.delete(userToBeDeleted);
    }

    public boolean checkIfCorrespondingUserExists(UUID userId){
        User correspondingUser = this.getUserByRepo(userId);
        return true;
    }

    private User getUserByRepo(UUID userId){
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