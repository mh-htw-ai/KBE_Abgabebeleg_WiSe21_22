package com.evilcorp.main_component_microservice.services.data_warehouse_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class  Film {

    private UUID id;
    private String titel;
    private double leihPreis;
}
