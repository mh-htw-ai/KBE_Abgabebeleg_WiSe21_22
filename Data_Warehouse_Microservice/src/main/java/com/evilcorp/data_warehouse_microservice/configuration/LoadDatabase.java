package com.evilcorp.data_warehouse_microservice.configuration;

import com.evilcorp.data_warehouse_microservice.FilmObj;
import com.evilcorp.data_warehouse_microservice.FilmObjRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    //Dies solle eine Standarddatebank implementieren
    @Bean
    CommandLineRunner initDatabase(FilmObjRepository filmObjRepository) {

        return args -> {
            log.info("====================> Preloading von Warehouse-Datenabnk-Content. <====================");
            if(filmObjRepository.count() == 0){
                log.info("====================> Filme Preloading Start <====================");
                FilmObj f1 = FilmObj.builder().uuid_film(UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117")).titel("Dune").leihPreis(2.0).build();
                filmObjRepository.save(f1);
                log.info(f1.toString());
                FilmObj f2 = FilmObj.builder().uuid_film(UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212")).titel("Matrix").leihPreis(3.0).build();
                filmObjRepository.save(f2);
                log.info(f2.toString());
                FilmObj f3 = FilmObj.builder().uuid_film(UUID.fromString("1f79ce68-33e2-4c0e-aee5-43b148e2a457")).titel("Crank").leihPreis(4.5).build();
                filmObjRepository.save(f3);
                log.info(f3.toString());
                FilmObj f4 = FilmObj.builder().uuid_film(UUID.fromString("975646a3-2895-40a5-9c0a-39368cef6891")).titel("Crank").leihPreis(4.5).geloescht(true).build();
                filmObjRepository.save(f4);
                log.info(f4.toString());
                log.info("Configuration: Datenbank hat Standart-Datensätze(" + filmObjRepository.count() + "Stück) erhalten.");
            }
            log.info("====================> Preloading ist abgeschlossen. <====================");
        };


    }
}