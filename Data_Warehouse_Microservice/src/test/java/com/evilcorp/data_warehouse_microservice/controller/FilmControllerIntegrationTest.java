package com.evilcorp.data_warehouse_microservice.controller;

import org.junit.jupiter.api.Test;

//@SpringBootTest(classes = DataWarehouseMicroserviceApplication.class
//        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

/*
@RunWith(SpringRunner.class)
@SpringBootTest(
        SpringBootTest.WebEnvironment.MOCK,
        classes = DataWarehouseMicroserviceApplicationTests.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")

 */
class FilmControllerIntegrationTest {
/*
    private static final Logger logger = LoggerFactory.getLogger(FilmControllerIntegrationTest.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FilmObjRepository filmObjRepository;

*/
@Test
void getFilmAll() {




    //createTestEmployee("bob");
/*
    mvc.perform(get("/api/employees")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", is("bob")));
*/
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