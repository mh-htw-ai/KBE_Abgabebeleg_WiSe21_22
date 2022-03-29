package com.evilcorp.main_component_microservice.movie.services.mwst_calculator_service;

import com.evilcorp.main_component_microservice.movie.services.ServiceNotAvailableException;
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

    private final String mwstCalculatorURI = "http://mwst:21111/mwst/api/v1.0/calculate_json";
    private final RestTemplate restTemplate = new RestTemplate();

    public Movie calculateCostWithMwstFor(Movie movieObj){
        HttpEntity<MwStObj> request = this.createMwStRequestObj(movieObj);
        MwStObj responseBean = this.exchangeForResponse(request);
        double priceWithMwst = responseBean.getArtMitSteuer();
        movieObj.setLeihPreis(priceWithMwst);
        return movieObj;
    }

    private HttpEntity<MwStObj> createMwStRequestObj(Movie movieObj){
        float costWithoutMwst = (float) movieObj.getLeihPreis();
        MwStObj requestContent = new MwStObj(costWithoutMwst);
        return new HttpEntity<>(requestContent);
    }

    private MwStObj exchangeForResponse(HttpEntity<MwStObj> request){
        ResponseEntity<MwStObj> response;
        try {
            response = restTemplate.exchange(mwstCalculatorURI, HttpMethod.PUT, request, MwStObj.class);
        }catch( HttpClientErrorException | ResourceAccessException e){
            throw new ServiceNotAvailableException();
        }
        return response.getBody();
    }
}
