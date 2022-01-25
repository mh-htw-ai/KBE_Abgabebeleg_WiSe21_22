package com.evilcorp.main_component_microservice.movie.services.external_api_service;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class ExternalApiService {

    private final static String deepLURI = "https://api-free.deepl.com/v2/translate";
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${deepL_api_authkey}")
    private String authkey;


    public String translateMovieDescription(String text){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("auth_key",authkey);
        body.add("text",text);
        body.add("target_lang","DE");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<DeepLResponseObj> responseEntity = restTemplate.exchange(deepLURI, HttpMethod.POST, requestEntity, DeepLResponseObj.class);


        List<TranslationObj> translations;
        try {
            translations = responseEntity.getBody().getTranslations();
        }catch(NullPointerException e){
            return "fail";
        }


        return translations.get(0).getText();
    }
}