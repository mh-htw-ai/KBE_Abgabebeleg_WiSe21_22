package com.evilcorp.data_warehouse_microservice.repository;

import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmObjBewertungRepository extends JpaRepository<FilmObjBewertung, Long> {

}
