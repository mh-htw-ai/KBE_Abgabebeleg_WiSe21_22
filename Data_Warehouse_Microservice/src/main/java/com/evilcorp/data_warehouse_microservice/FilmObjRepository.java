package com.evilcorp.data_warehouse_microservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FilmObjRepository extends JpaRepository<FilmObj, UUID> {


    /**
     * Gibt alle Filme zurueck, welche noch nicht geloscht wurden
     * @return Liste an ungeloeschten Filme
     */
    List<FilmObj> findAllByGeloeschtFalse();

    /**
     * Gibt nur ein Film zurueck, wenn der Film nicht bereits geloescht wurde
     * @param id des Filmes
     * @return Film der noch nicht geloescht wurde
     */
    //FilmObj findByUuid_filmAndGeloeschtFalse(UUID uuid_film);
    //FilmObj findFilmObjByUuid_filmAndGeloeschtFalse(UUID uuid_film);
    //FilmObj findFilmObjByUuid_filmAndGeloeschtIsFalse(UUID uuid_film);
    FilmObj findByIdAndGeloeschtIsFalse(UUID id);

    /**
     * Ermittelt, ob es ein Film gibt mit der uebergebenden UUID der noch nicht geloescht wurde.
     * @return true, wenn der Film vorhanden und noch nicht geloescht wurde
     */
    //boolean existsByIdAndGeloeschtIsFalse(UUID id);
   // boolean existsByIdAndGeloeschtIsFalse(UUID id);
    //boolean existsFilmObjByGeloeschtIsFalseAndUuid_film(UUID uuid_film);
    //boolean existsFilmObjByGeloeschtIsFalse();
    //boolean existsFilmObjByGeloescht(boolean geloescht);
    //boolean existsFilmObjById(UUID id);
    boolean existsFilmObjByIdAndGeloeschtIsFalse(UUID id);
    //boolean existsByUuid_filmAndGeloescht(UUID uuid_film, boolean geloescht);
    //boolean existsById(UUID id);
}
