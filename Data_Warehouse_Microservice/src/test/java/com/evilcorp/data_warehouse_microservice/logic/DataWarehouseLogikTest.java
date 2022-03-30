package com.evilcorp.data_warehouse_microservice.logic;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.*;

class DataWarehouseLogikTest {

    @Test
    void checkAcceptWithWrongMTAsString() {
        MediaType wrongMT = MediaType.APPLICATION_PDF;
        MediaType ergMT = DataWarehouseLogik.checkAccept(wrongMT.toString());
        assertNull(ergMT);
    }


    @Test
    void checkAcceptWithWrongMT() {
        MediaType wrongMT = MediaType.APPLICATION_PDF;
        MediaType ergMT = DataWarehouseLogik.checkAccept(wrongMT);
        assertNull(ergMT);
    }


    @Test
    void checkAcceptSucced() {
        MediaType actMT = MediaType.APPLICATION_XML;
        MediaType ergMT = DataWarehouseLogik.checkAccept(actMT);
        assertNotNull(ergMT);
        assertEquals(ergMT, actMT);

        actMT = MediaType.APPLICATION_JSON;
        ergMT = DataWarehouseLogik.checkAccept(actMT);
        assertNotNull(ergMT);
        assertEquals(ergMT, actMT);

        actMT = MediaType.ALL;
        ergMT = DataWarehouseLogik.checkAccept(actMT);
        assertNotNull(ergMT);
        assertEquals(ergMT, MediaType.APPLICATION_JSON);
    }
}