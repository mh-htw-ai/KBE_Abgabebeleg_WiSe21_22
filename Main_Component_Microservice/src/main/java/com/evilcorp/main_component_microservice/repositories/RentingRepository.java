package com.evilcorp.main_component_microservice.repositories;

import com.evilcorp.main_component_microservice.model_classes.MovieRenting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentingRepository extends JpaRepository<MovieRenting, UUID> {
}
