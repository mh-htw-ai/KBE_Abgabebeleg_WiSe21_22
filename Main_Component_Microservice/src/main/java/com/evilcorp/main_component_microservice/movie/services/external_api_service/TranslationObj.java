package com.evilcorp.main_component_microservice.movie.services.external_api_service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TranslationObj {

    private String detected_source_language;
    private String text;

}
