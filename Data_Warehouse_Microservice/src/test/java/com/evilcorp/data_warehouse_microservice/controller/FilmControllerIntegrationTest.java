package com.evilcorp.data_warehouse_microservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String path = "http://localhost:21139/film";

    @Test
    void getNewUUIDForFilmFromDataWareHouse() throws Exception {
        mockMvc.perform(get(path + "/newUuid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")));
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
    }



    /*
    @Test
    void getFilmAll() {
        String sPath = "/all";
        //String p = "http://localhost:21131/film/all";
        String url = "http://127.0.0.1:21131/film/all";

        Assert.notNull(url, "'url' must not be null");

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        //ResponseEntity<String> response = testRestTemplate.getForEntity(path + sPath, String.class);
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        //assertEquals(response.getStatusCode(), HttpStatus.OK);

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/spring-rest/foos";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

     */
/*
    @Test
    void getFilmByHeader() {
        fail("test not implement");
    }

    @Test
    void getFilmByPath() {
        fail("test not implement");
    }

    @Test
    void getFilmByTitle() {
        fail("test not implement");
    }

    @Test
    void postFilms() {
        fail("test not implement");
    }

    @Test
    void deleteFilm() {
        fail("test not implement");
    }

    @Test
    void putFilm() {
        fail("test not implement");
    }
    */
}