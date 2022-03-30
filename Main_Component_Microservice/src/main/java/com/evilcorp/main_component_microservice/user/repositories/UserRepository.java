package com.evilcorp.main_component_microservice.user.repositories;

import com.evilcorp.main_component_microservice.user.model_classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
