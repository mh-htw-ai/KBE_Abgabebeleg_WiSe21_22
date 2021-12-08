package com.evilcorp.mwst_microservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Mehrwertsteuerart {

    @JsonProperty("standard")
    STANDARD(0.19f),
    @JsonProperty("reduziert")
    REDUZIERT(0.07f);

    private final float prozentsatz;

    Mehrwertsteuerart(float prozentsatz){
        this.prozentsatz = prozentsatz;
    }

    public float getProzentsatz() {
        return prozentsatz;
    }
}
