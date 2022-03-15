package com.evilcorp.data_warehouse_microservice;

//@Configuration
//@EnableAutoConfiguration
//@ComponentScan("com.evilcorp.data_warehouse_microservice")

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DataWarehouseMicroserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    //TODO: Hier weitermachen mit Anleitung: https://github.com/spring-guides/gs-testing-web/blob/main/complete/src/test/java/com/example/testingweb/TestingWebApplicationTest.java
    //TODO: Hier die Übersicht: https://github.com/spring-guides/gs-testing-web/tree/main/complete <===
    //TODO: Nach folgender Anleitung: https://spring.io/guides/gs/testing-web/ bei Schritt "Es ist schön, eine Plausibilitätsprüfung"...
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

}
