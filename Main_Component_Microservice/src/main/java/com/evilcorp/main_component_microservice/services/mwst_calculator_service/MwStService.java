package com.evilcorp.main_component_microservice.services.mwst_calculator_service;

import com.evilcorp.main_component_microservice.services.data_warehouse_service.Film;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
public class MwStService {

    private static final String mwstCalculatorURI = "http://localhost:21111/mwst/json_request";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static Film calculateCostWithMwstFor(Film filmObj){

        float costWithoutMwst = (float) filmObj.getLeihPreis();
        MwStBean requestContent = new MwStBean(costWithoutMwst);
        HttpEntity<MwStBean> request = new HttpEntity<>(requestContent);

        ResponseEntity<MwStBean> response = restTemplate.exchange(mwstCalculatorURI, HttpMethod.PUT, request, MwStBean.class);
        MwStBean responseBean = response.getBody();
        assert responseBean != null;

        double priceWithMwst = responseBean.getArtMitSteuer();
        filmObj.setLeihPreis(priceWithMwst);

        return filmObj;
    }

    public static Film[] calculateCostWithMwstForMultipleFilms(Film[] filmObjs){
        for(Film film : filmObjs){
            film = MwStService.calculateCostWithMwstFor(film);
        }
        return filmObjs;
    }



}
