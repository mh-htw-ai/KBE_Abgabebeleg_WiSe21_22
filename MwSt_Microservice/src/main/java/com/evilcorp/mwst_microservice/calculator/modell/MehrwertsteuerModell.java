package com.evilcorp.mwst_microservice.calculator.modell;

import com.evilcorp.mwst_microservice.calculator.modell.Mehrwertsteuerart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter @Setter
public class MehrwertsteuerModell {

    @DecimalMin(value="0.0")
    private float Artikelpreis;

    @DecimalMin(value="0.0")
    private float Steueranteil;

    @DecimalMin(value="0.0")
    private float ArtMitSteuer;

    @NotNull
    private Mehrwertsteuerart Steuertyp;
}
