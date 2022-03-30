package com.evilcorp.data_warehouse_microservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BewertungControllerIntegrationTest {





    @Test
    public void greetingShouldReturnDefaultMessage(){

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