package com.evilcorp.mwst_microservice.calculator.modell;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Mehrwertsteuerart {

    @JsonProperty("standard")
    STANDARD(0.19f),
    @JsonProperty("reduziert")
    REDUZIERT(0.07f);

    private final float prozentsatz;
}
