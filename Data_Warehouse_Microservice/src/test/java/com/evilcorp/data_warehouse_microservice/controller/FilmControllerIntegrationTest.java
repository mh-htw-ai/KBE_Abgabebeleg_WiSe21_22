package com.evilcorp.data_warehouse_microservice.controller;


import com.evilcorp.data_warehouse_microservice.model.FilmObj;
import com.evilcorp.data_warehouse_microservice.repository.FilmObjRepository;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmObjRepository filmObjRepository;

    private final String path = "http://localhost:21139/film";

    private final static ArrayList<FilmObj> filmObjList = new ArrayList<>();


    @BeforeAll
    static void beforeAll() {
        FilmObj f1 = FilmObj.builder()
                .id(UUID.fromString("478d70fd-c572-4ca6-bd08-61f165380117"))
                .titel("Dune")
                .leihPreis(2.0)
                .kurzbeschreibung("Film über einem wertvollen Wüstenplanet in einer dystopischen Zukunft.")
                .beschreibung("Dune (alternativ auch: Dune: Part One) ist ein US-amerikanischer Science-Fiction-Film des kanadischen Regisseurs Denis Villeneuve. Die Handlung basiert auf der ersten Hälfte des ersten Buches der gleichnamigen Romanreihe (1965) von Frank Herbert und ist nach dem Kinofilm Der Wüstenplanet (1984) von David Lynch und der Miniserie Dune – Der Wüstenplanet (2000) von John Harrison die dritte Adaption der literarischen Vorlage. Seine Weltpremiere feierte der Film am 3. September 2021 bei den Internationalen Festspielen von Venedig, die deutsche Premiere war am 16. September 2021 und der US-amerikanische Kinostart am 22. Oktober 2021, wurde jedoch bereits einen Tag zuvor erstmals in Vorpremieren und auf HBO Max gezeigt")
                .build();
        filmObjList.add(f1);
        FilmObj f2 = FilmObj.builder()
                .id(UUID.fromString("74a8c6a2-37ae-4409-a994-1282149f9212"))
                .titel("Matrix")
                .leihPreis(3.0)
                .kurzbeschreibung("Eine virtuelle Welt, welche als Matrix bezeichnet wird. zeigt die aktuelle Gegenwart nur um eine dystopische Zukunft zu verbergen.")
                .beschreibung("Matrix (englischer Originaltitel: The Matrix) ist ein Science-Fiction-Film aus dem Jahr 1999. Regie führten die Wachowskis, die auch das Drehbuch schrieben und zum Zeitpunkt des Erscheinens noch unter dem Namen „The Wachowski Brothers“ auftraten. Die Hauptrollen spielten Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss und Hugo Weaving. Im Mai 2003 folgte die Fortsetzung mit Matrix Reloaded, im November 2003 der dritte Teil Matrix Revolutions und im Dezember 2021 Teil 4 mit Matrix Resurrections. ")
                .build();
        filmObjList.add(f2);
        FilmObj f3 = FilmObj.builder()
                .id(UUID.fromString("1f79ce68-33e2-4c0e-aee5-43b148e2a457"))
                .titel("Crank")
                .leihPreis(4.5)
                .kurzbeschreibung("Ein Auftragmörder wird vergiftet und will Rache.")
                .beschreibung("Crank (übersetzt: Spinner, Sonderling) ist ein Actionfilm aus dem Jahr 2006, der mit Jason Statham, Amy Smart und Dwight Yoakam gedreht wurde. Die Regie des US-amerikanischen Spielfilms führten Mark Neveldine und Brian Taylor, die damit ihr Spielfilmdebüt ablieferten. Die Produktionskosten betrugen 12 Millionen Euro. Offizieller Deutschlandstart des Films war der 21. September 2006.")
                .build();
        filmObjList.add(f3);
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
        filmObjList.add(f4);
        FilmObj f5 = FilmObj.builder()
                .id(UUID.fromString("4221722d-7072-4ba8-b377-014f5f5814ce"))
                .titel("Crank")
                .leihPreis(4.5)
                .kurzbeschreibung("Ein Auftragmörder wird vergiftet und will Rache.")
                .beschreibung("Crank (übersetzt: Spinner, Sonderling) ist ein Actionfilm aus dem Jahr 2006, der mit Jason Statham, Amy Smart und Dwight Yoakam gedreht wurde. Die Regie des US-amerikanischen Spielfilms führten Mark Neveldine und Brian Taylor, die damit ihr Spielfilmdebüt ablieferten. Die Produktionskosten betrugen 12 Millionen Euro. Offizieller Deutschlandstart des Films war der 21. September 2006.")
                .geloescht(true)
                .build();
        filmObjList.add(f5);
    }

    @BeforeEach
    void setUp() {
        filmObjRepository.deleteAll();
        if (filmObjRepository.count() == 0) {
            filmObjRepository.saveAll(filmObjList);
        }
    }

    @Test
    void getNewUUIDForFilmFromDataWareHouse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(path + "/newUuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andReturn();
        try {
            UUID uuid =  UUID.fromString(mvcResult.getResponse().getContentAsString());
            if(uuid.toString().equals("")){
                fail("Es wurde keine UUID erstellt werden.");
            }
        } catch (IllegalArgumentException ex) {
            Assertions.fail("String inkorrekt for UUID.");
        }
    }


    @Test
    void failureRequestsNewUUIDForFilmFromDataWareHouse() throws Exception {
        mockMvc.perform(put(path + "/newUuid"))
                .andExpect(status().isMethodNotAllowed());
        mockMvc.perform(post(path + "/newUuid"))
                .andExpect(status().isMethodNotAllowed());
        mockMvc.perform(delete(path + "/newUuid"))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    void getFilmAllMediaTypes() throws Exception {
        String endpoint = "/all";

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML));

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_PDF))
                .andExpect(status().isUnsupportedMediaType());

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnsupportedMediaType());

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.IMAGE_GIF))
                .andExpect(status().isUnsupportedMediaType());

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isUnsupportedMediaType());
    }


    @Test
    void getFilmAllJSONTest() throws Exception {
        String endpoint = "/all";
        List<FilmObj> listeFromDB = filmObjRepository.findAllByGeloeschtFalse();

        MvcResult mvcResult = mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse res = mvcResult.getResponse();
        Assertions.assertEquals(MediaType.APPLICATION_JSON, MediaType.parseMediaType(Objects.requireNonNull(res.getContentType())));

        String body = res.getContentAsString();
        JSONArray ar = new JSONArray(body);
        Assertions.assertEquals(listeFromDB.size(), ar.length());
    }


    @Test
    void failureFilmAllJSONTest() throws Exception {
        String endpoint = "/all";

        mockMvc.perform(post(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(delete(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    void getFilmByHeaderNotFoundTest() throws Exception {
        String endpoint = "/";

        MvcResult mvcResult = mockMvc.perform(get(path + "/newUuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andReturn();

        UUID uuid = UUID.fromString(mvcResult.getResponse().getContentAsString());

        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("UUID", uuid))
                .andExpect(status().isNotFound());
    }


    @Test
    void getFilmByHeaderInJson() throws Exception {
        String endpoint = "/";

        UUID uuid = getNewUUID();
        mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("UUID", uuid))
                .andExpect(status().isNotFound());

        FilmObj filmObj = FilmObj.builder()
                .id(uuid)
                .titel("test_filmObj")
                .leihPreis(5.5)
                .kurzbeschreibung("test_kurzbeschreibung")
                .beschreibung("test_beschreibung")
                .geloescht(false)
                .build();

        filmObjRepository.save(filmObj);

        MvcResult mvcResult = mockMvc.perform(get(path + endpoint)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("UUID", uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON, MediaType.parseMediaType(Objects.requireNonNull(mvcResult.getResponse().getContentType())));

        }


    private UUID getNewUUID (){
        int i = 1000;
        UUID uuid;
        while(true){
            uuid = UUID.randomUUID();
            if(!filmObjRepository.existsById(uuid)){
                return uuid;
            }
            i--;
            if(i == 0){
                throw new IndexOutOfBoundsException("getNewUUID konnte keine neue UUID erzeugen.");
            }
        }
    }
}