package com.evilcorp.main_component_microservice.movie.services.mwst_calculator_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MwStObj {

    private float artikelpreis;
    private float steueranteil;
    private float artMitSteuer;
    private String steuertyp;

    public MwStObj(float Artikelpreis){
        this.artikelpreis = Artikelpreis;
        this.steueranteil = 0f;
        this.artMitSteuer = 0f;
        this.steuertyp = "standard";
    }


}
