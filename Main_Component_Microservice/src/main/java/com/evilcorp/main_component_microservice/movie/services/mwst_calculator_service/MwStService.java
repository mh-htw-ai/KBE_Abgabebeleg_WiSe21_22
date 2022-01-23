package com.evilcorp.main_component_microservice.movie.services.mwst_calculator_service;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MwStService {

    private final String mwstCalculatorURI = "http://localhost:21111/mwst/json_request";
    private final RestTemplate restTemplate = new RestTemplate();

    public Movie calculateCostWithMwstFor(Movie movieObj){

        float costWithoutMwst = (float) movieObj.getLeihPreis();
        MwStBean requestContent = new MwStBean(costWithoutMwst);
        HttpEntity<MwStBean> request = new HttpEntity<>(requestContent);

        ResponseEntity<MwStBean> response;

        try {
            response = restTemplate.exchange(mwstCalculatorURI, HttpMethod.PUT, request, MwStBean.class);
        }catch( HttpClientErrorException e){
            e.printStackTrace();
            return movieObj;
        }catch (ResourceAccessException e){
            log.error("Mwst-Microservice could not be reached!");
            log.error("Mwst-Microservice might not have been started!");
            return movieObj;
        }

        MwStBean responseBean = response.getBody();
        assert responseBean != null;

        double priceWithMwst = responseBean.getArtMitSteuer();
        movieObj.setLeihPreis(priceWithMwst);

        return movieObj;
    }

}
