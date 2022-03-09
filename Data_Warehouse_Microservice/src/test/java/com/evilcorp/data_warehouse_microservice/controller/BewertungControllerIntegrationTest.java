package com.evilcorp.data_warehouse_microservice.controller;

import com.evilcorp.data_warehouse_microservice.DataWarehouseMicroserviceApplication;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/*
@RunWith(SpringRunner.class)
@SpringBootTest(
        //SpringBootTest.WebEnvironment.MOCK, //Ist auch der Defaultwert
        classes = DataWarehouseMicroserviceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:\\application-integrationstest.properties")
        */
/*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataWarehouseMicroserviceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
 */

@SpringBootTest(classes = DataWarehouseMicroserviceApplication.class
        , webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class BewertungControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

   // @Autowired
   // private FilmObjBewertungRepository filmObjBewertungRepository;

    //@Autowired
    //private BewertungController bewertungController;


    private String testfilePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "testfile_movie_rating_20220216_1655.csv";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Autowired
    private BewertungController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }


    @Test
    public void greetingShouldReturnMessageFromService() {
        //mockMvc


        //when(bewertungController.getStartImport(testfilePath).getStatusCode().is2xxSuccessful())

/*
        this.mockMvc.perform(
                get(File.separator + "importer" + File.separator + "import")
                        .pathInfo("test.csv")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mock")));
        System.err.println("======> Ausgabe: Port=" + port);
        */

        //fail("test not implement");
    }


    @Test
    public void getStartImport() {
        //fail("test not implement");
    }

    @Test
    public void getStartExport() {
    }
}