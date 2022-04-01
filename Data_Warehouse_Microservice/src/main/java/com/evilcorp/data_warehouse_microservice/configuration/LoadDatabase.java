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

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    //Dies solle in eine Standarddatenbank immer neu implementiert werden
    @Bean
    CommandLineRunner initDatabase(FilmObjRepository filmObjRepository, FilmObjBewertungRepository filmObjBewertungRepository) {

        return args -> {
            log.info("====================> Preloading von Warehouse-Datenabnk-Content. <====================");
            if(filmObjRepository.count() == 0){
                log.info("====================> Filme Preloading Start <====================");
                FilmObj f1 = FilmObj.builder()
                        .id(UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117"))
                        .titel("Dune")
                        .leihPreis(2.0)
                        .kurzbeschreibung("Film über einem wertvollen Wüstenplanet in einer dystopischen Zukunft.")
                        .beschreibung("Dune (alternativ auch: Dune: Part One) ist ein US-amerikanischer Science-Fiction-Film des kanadischen Regisseurs Denis Villeneuve. Die Handlung basiert auf der ersten Hälfte des ersten Buches der gleichnamigen Romanreihe (1965) von Frank Herbert und ist nach dem Kinofilm Der Wüstenplanet (1984) von David Lynch und der Miniserie Dune – Der Wüstenplanet (2000) von John Harrison die dritte Adaption der literarischen Vorlage. Seine Weltpremiere feierte der Film am 3. September 2021 bei den Internationalen Festspielen von Venedig, die deutsche Premiere war am 16. September 2021 und der US-amerikanische Kinostart am 22. Oktober 2021, wurde jedoch bereits einen Tag zuvor erstmals in Vorpremieren und auf HBO Max gezeigt")
                        .build();
                filmObjRepository.save(f1);
                log.info(f1.toString());
                FilmObj f2 = FilmObj.builder()
                        .id(UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212"))
                        .titel("Matrix")
                        .leihPreis(3.0)
                        .kurzbeschreibung("Eine virtuelle Welt, welche als Matrix bezeichnet wird. zeigt die aktuelle Gegenwart nur um eine dystopische Zukunft zu verbergen.")
                        .beschreibung("Matrix (englischer Originaltitel: The Matrix) ist ein Science-Fiction-Film aus dem Jahr 1999. Regie führten die Wachowskis, die auch das Drehbuch schrieben und zum Zeitpunkt des Erscheinens noch unter dem Namen „The Wachowski Brothers“ auftraten. Die Hauptrollen spielten Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss und Hugo Weaving. Im Mai 2003 folgte die Fortsetzung mit Matrix Reloaded, im November 2003 der dritte Teil Matrix Revolutions und im Dezember 2021 Teil 4 mit Matrix Resurrections. ")
                        .build();
                filmObjRepository.save(f2);
                log.info(f2.toString());
                FilmObj f3 = FilmObj.builder()
                        .id(UUID.fromString("1f79ce68-33e2-4c0e-aee5-43b148e2a457"))
                        .titel("Crank")
                        .leihPreis(4.5)
                        .kurzbeschreibung("Ein Auftragmörder wird vergiftet und will Rache.")
                        .beschreibung("Crank (übersetzt: Spinner, Sonderling) ist ein Actionfilm aus dem Jahr 2006, der mit Jason Statham, Amy Smart und Dwight Yoakam gedreht wurde. Die Regie des US-amerikanischen Spielfilms führten Mark Neveldine und Brian Taylor, die damit ihr Spielfilmdebüt ablieferten. Die Produktionskosten betrugen 12 Millionen Euro. Offizieller Deutschlandstart des Films war der 21. September 2006.")
                        .build();
                filmObjRepository.save(f3);
                log.info(f3.toString());
                FilmObj f4 = FilmObj.builder()
                        .id(UUID.fromString("975646a3-2895-40a5-9c0a-39368cef6891"))
                        .titel("District 9")
                        .leihPreis(4.5)
                        .kurzbeschreibung("")
                        .beschreibung("Im Jahr 1982 stoppt ein riesiges außerirdisches Raumschiff über Johannesburg in Südafrika. Nachdem sich angeblich ein Kommandomodul vom Schiff gelöst hat und auf die Erde gefallen ist, ohne jemals von Menschen gefunden worden zu sein, verharrt das Raumschiff seit seiner Ankunft unbeweglich über der Stadt. Nach dreimonatigem ereignislosem Warten entschließen sich die Menschen, einen Weg in das Raumschiff zu schneiden. Dort finden sie über eine Million insektoide Außerirdische in einem gesundheitlich sehr schlechten Zustand vor. "
                                + "Ein Kommandant des Schiffes wird nicht gefunden. Die extraterrestrischen Wesen werden in einem behelfsmäßigen Flüchtlingslager untergebracht, das den Namen District 9 trägt und sich rasch zu einem Slum entwickelt. Im ersten Jahrzehnt des 21. Jahrhunderts ist die Multinational United (MNU), ein privates Sicherheits- und Militärunternehmen, verantwortlich für die Überwachung der mittlerweile 1,8 Millionen Insektoiden. "
                                + "Wie sich herausstellt, ist die MNU jedoch weniger am Wohl ihrer Schützlinge als an deren Waffentechnik interessiert, mit der sie immense Profite erzielen will. Doch die MNU scheitert, denn um die außerirdischen Waffen zu aktivieren, benötigen sie außerirdische DNA. Im Rahmen einer geplanten Umsiedlung der Aliens nach District 10, einem Camp etwa 200 Kilometer nordwestlich von Johannesburg, hat der MNU-Mitarbeiter Wikus van de Merwe Kontakt mit einer außerirdischen Flüssigkeit. "
                                + "Auf einer Überraschungsfeier aufgrund seiner Beförderung zum Zuständigen der Umsiedlungsaktion bricht er zusammen und wird in ein Krankenhaus eingeliefert. Dort stellt sich heraus, dass die Flüssigkeit seine DNA verändert. So mutiert sein linker Arm zu dem eines Aliens und auch seine anderen menschlichen Körperteile und -funktionen werden den Außerirdischen immer ähnlicher. Im Krankenhaus wird Wikus kurze Zeit später von der MNU überwältigt und in ein unterirdisches Labor transportiert, in dem Experimente an den Außerirdischen vorgenommen werden. "
                                + "Die MNU stellt fest, dass Wikus fähig ist, die außerirdischen Waffen zu aktivieren, und missbraucht auch ihn zu Experimenten. Kurz bevor er von den Wissenschaftlern der Firma viviseziert werden soll, gelingt Wikus jedoch die Flucht, woraufhin er aufgrund seines immensen Wertes zum meistgesuchten Mann der Welt wird. Die MNU hetzt die Bevölkerung durch gefälschte Propagandainformationen im Fernsehen und gezielte Fehlinformation seiner Familie und Freunde gegen Wikus auf. Nun gibt es für ihn nur einen Ort, um sich zu verstecken: District 9. "
                                + "Dort trifft er wieder auf den Alien Christopher. Der sammelt auf Schrottplätzen eine mutagene Flüssigkeit, die in technischen Gerätschaften der Außerirdischen zu finden ist. Christopher und sein Sohn leben in einer Hütte, unter der ein Flugmodul mit dem Kommandomodul versteckt ist; die Flüssigkeit dient ihm als Treibstoff. Christopher verspricht Wikus, dessen genetische Veränderungen rückgängig zu machen, wenn dieser ihm hilft, die durch Wikus zuvor konfiszierte Flüssigkeit von der MNU zurückzubekommen. "
                                + "Wikus gelingt es, einer kriminellen Bande von Nigerianern außerirdische Waffen zu entwenden. Mit Hilfe dieser überlegenen Technik brechen er und Christopher erfolgreich in das MNU-Labor ein und entwenden die Flüssigkeit. Dort wird Christopher zum ersten Mal damit konfrontiert, dass die Menschen in unterirdischen Labors medizinische Experimente an den Aliens vornehmen, was ihn zutiefst schockiert."
                                + "Bei ihrer Rückkehr in den District 9 kommt es dann zum Endkampf mit den Sicherheitskräften der MNU und den Nigerianern. Wikus und Christopher werden von den MNU-Sicherheitskräften festgenommen. Wikus wird von den Nigerianern befreit und zu deren Bandenführer gebracht, welcher Wikus’ Arm essen möchte, um dessen Kräfte zu erlangen. Christophers Sohn schafft es, über das Mutterschiff einen außerirdischen Kampfanzug, der im Inneren des Hauptquartiers steht, zu aktivieren. "
                                + "Mit Hilfe dieses außerirdischen Kampfanzuges versucht Wikus, die MNU-Truppen und die nigerianischen Bandenmitglieder allein hinzuhalten, um Christopher und dessen Sohn die Flucht in das Mutterschiff zu ermöglichen. Der Plan gelingt, und die beiden treten samt Mutterschiff die Rückreise zu ihrem Heimatplaneten an. Christopher hat Wikus bei ihrem Abschied versprochen, in drei Jahren zurückzukehren, um ihn von seiner Mutation zu heilen."
                                + "Die Spekulationen der Sicherheitskräfte, was Christopher bei einer etwaigen Rückkehr auf die Erde wirklich tun würde, gehen auseinander. Es wird gemutmaßt, er könnte einen Plan zur Rettung seines Volkes aufstellen oder aber auch der Menschheit den Krieg erklären. Der neue District 10 bietet nun 2,5 Millionen Außerirdischen eine Heimat und wächst ständig weiter. Die letzte Einstellung des Filmes zeigt Wikus, der inzwischen völlig zum Alien mutiert ist. "
                                + "Er bastelt aus Schrott Blumen und legt diese heimlich vor die Tür seiner Frau. ")
                        .build();
                filmObjRepository.save(f4);
                log.info(f4.toString());
                FilmObj f5 = FilmObj.builder()
                        .id(UUID.fromString("4221722d-7072-4ba8-b377-014f5f5814ce"))
                        .titel("Crank")
                        .leihPreis(4.5)
                        .kurzbeschreibung("Ein Auftragmörder wird vergiftet und will Rache.")
                        .beschreibung("Crank (übersetzt: Spinner, Sonderling) ist ein Actionfilm aus dem Jahr 2006, der mit Jason Statham, Amy Smart und Dwight Yoakam gedreht wurde. Die Regie des US-amerikanischen Spielfilms führten Mark Neveldine und Brian Taylor, die damit ihr Spielfilmdebüt ablieferten. Die Produktionskosten betrugen 12 Millionen Euro. Offizieller Deutschlandstart des Films war der 21. September 2006.")
                        .geloescht(true)
                        .build();
                filmObjRepository.save(f5);
                log.info(f5.toString());
                log.info("Configuration: Datenbank(Filme) hat Standart-Datensätze(" + filmObjRepository.count() + "Stück) erhalten.");

                log.info("====================> Filme Preloading Abgeschlossen <====================");

                log.info("====================> Filmberwertungen Preloading Start <====================");

                FilmObjBewertung fb1_1 = FilmObjBewertung.builder()
                        .filmUuid(f1.getId())
                        .Gesamtwertung(44)
                        .Zuschauerzahl(22)
                        .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                        .build();
                filmObjBewertungRepository.save(fb1_1);
                log.info(fb1_1.toString());
                FilmObjBewertung fb1_2 = FilmObjBewertung.builder()
                        .filmUuid(f1.getId())
                        .Gesamtwertung(77)
                        .Zuschauerzahl(37)
                        .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                        .build();
                filmObjBewertungRepository.save(fb1_2);
                log.info(fb1_2.toString());
                FilmObjBewertung fb2_1 = FilmObjBewertung.builder()
                        .filmUuid(f2.getId())
                        .Gesamtwertung(66)
                        .Zuschauerzahl(33)
                        .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                        .build();
                filmObjBewertungRepository.save(fb2_1);
                log.info(fb2_1.toString());
                FilmObjBewertung fb2_2 = FilmObjBewertung.builder()
                        .filmUuid(f2.getId())
                        .Gesamtwertung(32)
                        .Zuschauerzahl(16)
                        .Erstellungsdatum(LocalDateTime.of(2012, 12, 12, 12, 12, 12))
                        .build();
                filmObjBewertungRepository.save(fb2_2);
                log.info(fb2_2.toString());

                log.info("Configuration: Datenbank(Filmbewertungen) hat Standart-Datensätze(" + filmObjBewertungRepository.count() + "Stück) erhalten.");
                log.info("====================> Filmberwertungen Preloading Abgeschlossen <====================");
            }
            log.info("====================> Preloading ist abgeschlossen. <====================");
        };


    }
}