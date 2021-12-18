package com.evilcorp.data_warehouse_microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;


public class DataWarehouseLogik {

    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);

    public static MediaType checkAccept(String accept){
           MediaType mt = MediaType.parseMediaType(accept);
        return checkAccept(mt);
    }

    public static MediaType checkAccept(MediaType mt){
        if(mt.equals(MediaType.ALL) || mt.equals(MediaType.APPLICATION_JSON)){
            return MediaType.APPLICATION_JSON;
        }
        if (mt.equals(MediaType.APPLICATION_XML)){
            return MediaType.APPLICATION_XML;
        }
        return null;
    }

}
