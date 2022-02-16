package com.evilcorp.main_component_microservice.movie.services.external_api_service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DeepLResponseObj {

    private List<TranslationObj> translations;
}
