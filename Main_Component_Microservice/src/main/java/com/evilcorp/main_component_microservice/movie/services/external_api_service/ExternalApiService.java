package com.evilcorp.main_component_microservice.movie.services.external_api_service;

import com.evilcorp.main_component_microservice.movie.model_classes.Movie;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    public String translateTextTest(String text){
        Movie wrapperMovie = new Movie();
        wrapperMovie.setKurzbeschreibung(text);
        wrapperMovie.setBeschreibung("  ");
        wrapperMovie = this.translateMovieDescriptions(wrapperMovie);
        return wrapperMovie.getKurzbeschreibung();
    }

    public Movie translateMovieDescriptions(Movie movie){
        String shortDescription = movie.getKurzbeschreibung();
        String description = movie.getBeschreibung();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("auth_key", authkey);
        body.add("text", shortDescription);
        body.add("text", description);
        body.add("target_lang", "EN");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<DeepLResponseObj> responseEntity = restTemplate.exchange(deepLURI, HttpMethod.POST, requestEntity, DeepLResponseObj.class);

        List<TranslationObj> translations;
        try {
            translations = responseEntity.getBody().getTranslations();
        }catch(NullPointerException e){
            return null;
        }

        String translatedShortDescription = translations.get(0).getText();
        String translatedDescription = translations.get(1).getText();

        movie.setKurzbeschreibung(translatedShortDescription);
        movie.setBeschreibung(translatedDescription);

        return movie;
    }
}