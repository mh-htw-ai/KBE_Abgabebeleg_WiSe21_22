package com.evilcorp.data_warehouse_microservice.configuration;

import com.evilcorp.data_warehouse_microservice.model.FilmObj;
import com.evilcorp.data_warehouse_microservice.model.FilmObjBewertung;
import com.evilcorp.data_warehouse_microservice.repository.FilmObjBewertungRepository;
import com.evilcorp.data_warehouse_microservice.repository.FilmObjRepository;
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
    CommandLineRunner initDatabase(FilmObjRepository filmObjRepository, FilmObjBewertungRepository filmObjBewertungRepository) {

        return args -> {
            log.info("====================> Preloading von Warehouse-Datenabnk-Content. <====================");
            if(filmObjRepository.count() == 0){
                log.info("====================> Filme Preloading Start <====================");

                FilmObj f1 = FilmObj.builder().id(UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117")).titel("Dune").leihPreis(2.0).build();
                filmObjRepository.save(f1);
                log.info(f1.toString());
                FilmObj f2 = FilmObj.builder().id(UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212")).titel("Matrix").leihPreis(3.0).build();
                filmObjRepository.save(f2);
                log.info(f2.toString());
                FilmObj f3 = FilmObj.builder().id(UUID.fromString("1f79ce68-33e2-4c0e-aee5-43b148e2a457")).titel("Crank").leihPreis(4.5).build();
                filmObjRepository.save(f3);
                log.info(f3.toString());
                FilmObj f4 = FilmObj.builder().id(UUID.fromString("975646a3-2895-40a5-9c0a-39368cef6891")).titel("Crank").leihPreis(4.5).geloescht(true).build();
                filmObjRepository.save(f4);
                log.info(f4.toString());
                log.info("Configuration: Datenbank(Filme) hat Standart-Datensätze(" + filmObjRepository.count() + "Stück) erhalten.");

                log.info("====================> Filme Preloading Abgeschlossen <====================");

                log.info("====================> Filmberwertungen Preloading Start <====================");

                FilmObjBewertung fb1_1 = FilmObjBewertung.builder().filmUuid(f1.getId()).Gesamtwertung(44).Zuschauerzahl(22).build();
                filmObjBewertungRepository.save(fb1_1);
                log.info(fb1_1.toString());
                FilmObjBewertung fb1_2 = FilmObjBewertung.builder().filmUuid(f1.getId()).Gesamtwertung(77).Zuschauerzahl(37).build();
                filmObjBewertungRepository.save(fb1_2);
                log.info(fb1_2.toString());
                FilmObjBewertung fb2_1 = FilmObjBewertung.builder().filmUuid(f2.getId()).Gesamtwertung(66).Zuschauerzahl(33).build();
                filmObjBewertungRepository.save(fb2_1);
                log.info(fb2_1.toString());
                FilmObjBewertung fb2_2 = FilmObjBewertung.builder().filmUuid(f2.getId()).Gesamtwertung(32).Zuschauerzahl(16).build();
                filmObjBewertungRepository.save(fb2_2);
                log.info(fb2_2.toString());

                log.info("Configuration: Datenbank(Filmbewertungen) hat Standart-Datensätze(" + filmObjBewertungRepository.count() + "Stück) erhalten.");
                log.info("====================> Filmberwertungen Preloading Abgeschlossen <====================");
            }
            log.info("====================> Preloading ist abgeschlossen. <====================");
        };


    }
}