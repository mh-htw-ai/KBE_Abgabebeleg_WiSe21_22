package com.evilcorp.data_warehouse_microservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilmObjRepository extends JpaRepository<FilmObj, UUID> {
}
