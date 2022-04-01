package com.evilcorp.data_warehouse_microservice.logic;

import com.evilcorp.data_warehouse_microservice.model.FilmObj;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;


public class DataWarehouseLogik {

    private static final Logger log = LoggerFactory.getLogger(FilmObj.class);


    /**
     * Funktion ueberprueft die erlaubten Mediatypen
     * @param accept angeforderter Mediatype als String
     * @return MediaType akzeptiert wird gibt er das Zielformat zurueck, bei falschem Mediatype null
     */
    public static MediaType checkAccept(String accept){
           MediaType mt = MediaType.parseMediaType(accept);
        return checkAccept(mt);
    }


    /**
     * Funktion ueberprueft die erlaubten Mediatypen
     * @param mt angeforderter Mediatype
     * @return MediaType akzeptiert wird gibt er das Zielformat zurueck, bei falschem Mediatype null
     */
    public static MediaType checkAccept(MediaType mt){
        if(mt.equals(MediaType.ALL) || mt.equals(MediaType.APPLICATION_JSON)){
            return MediaType.APPLICATION_JSON;
        }
        if (mt.equals(MediaType.APPLICATION_XML)){
            return MediaType.APPLICATION_XML;
        }
        return null;
    }


    /**
     * Funktion wandelt anhand des gewuenschten MediaTypes den gewünschten Mapper um
     *
     * @param mt - Zielt-MediaType
     * @return Mapper anhand des MediaTypes
     */
    public static ObjectMapper zielformatierung(MediaType mt) {
        ObjectMapper mapper;
        System.out.println("Zielformat: " + mt.toString());
        if (mt.equals(MediaType.APPLICATION_XML)) {
            mapper = new XmlMapper();
            System.out.println("Umwandlung in XML.");
        } else {
            mapper = new JsonMapper();
            System.out.println("Umwandlung in JSON.");
        }
        return mapper;
    }

}
