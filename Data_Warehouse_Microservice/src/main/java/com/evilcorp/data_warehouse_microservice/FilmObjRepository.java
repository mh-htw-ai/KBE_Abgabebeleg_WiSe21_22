package com.evilcorp.data_warehouse_microservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilmObjRepository extends JpaRepository<FilmObj, UUID> {


    /**
     * Gibt alle Filme zurueck, welche noch nicht geloscht wurden
     *
     * @return Liste an ungeloeschten Filme
     */
    List<FilmObj> findAllByGeloeschtFalse();

    /**
     * Gibt nur ein Film zurueck, wenn der Film nicht bereits geloescht wurde
     *
     * @param id des Filmes
     * @return Film der noch nicht geloescht wurde
     */
    FilmObj findByIdAndGeloeschtIsFalse(UUID id);

    /**
     * Ermittelt, ob es ein Film gibt mit der uebergebenden UUID der noch nicht geloescht wurde.
     *
     * @return true, wenn der Film vorhanden und noch nicht geloescht wurde
     */
    boolean existsFilmObjByIdAndGeloeschtIsFalse(UUID id);
}
