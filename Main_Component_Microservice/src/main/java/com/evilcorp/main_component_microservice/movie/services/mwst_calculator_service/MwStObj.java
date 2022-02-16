package com.evilcorp.main_component_microservice.movie.services.mwst_calculator_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MwStObj {

    private float Artikelpreis;
    private float Steueranteil;
    private float ArtMitSteuer;
    private String Steuertyp;

    public MwStObj(float Artikelpreis){
        this.Artikelpreis = Artikelpreis;
        this.Steueranteil = 0f;
        this.ArtMitSteuer = 0f;
        this.Steuertyp = "standard";
    }


}
