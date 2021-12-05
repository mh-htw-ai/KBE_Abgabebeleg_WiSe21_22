package com.evilcorp.data_warehouse_microservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilmRepository extends JpaRepository<FilmObj, UUID> {
}
