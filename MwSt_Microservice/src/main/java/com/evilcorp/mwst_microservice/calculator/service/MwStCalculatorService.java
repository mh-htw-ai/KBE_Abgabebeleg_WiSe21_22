package com.evilcorp.mwst_microservice.calculator.service;

import com.evilcorp.mwst_microservice.calculator.modell.MehrwertsteuerModell;
import com.evilcorp.mwst_microservice.calculator.modell.Mehrwertsteuerart;
import org.springframework.stereotype.Service;

@Service
public class MwStCalculatorService {

    public MehrwertsteuerModell calculate(MehrwertsteuerModell mwstModell){
        if(this.toMuchValuesAreZero(mwstModell))throw new ToMuchMissingValuesException();
        if(this.steuerartIsNull(mwstModell))throw new CouldNotDetermineSteuerartException();
        mwstModell = this.calculateMissingValues(mwstModell);
        return this.roundFloatingPointValues(mwstModell);
    }

    private float calculateArtikelpreis(float artMitSteuer, Mehrwertsteuerart mehrwertsteuerart){
        return artMitSteuer / (1+mehrwertsteuerart.getProzentsatz());
    }

    private float calculateSteueranteil(float artikelPreis, Mehrwertsteuerart mehrwertsteuerart){
        return artikelPreis * mehrwertsteuerart.getProzentsatz();
    }

    private float calculateArtMitSteuer(float artikelPreis, Mehrwertsteuerart mehrwertsteuerart){
        float steueranteil  = artikelPreis * mehrwertsteuerart.getProzentsatz();
        return artikelPreis + steueranteil;
    }

    private MehrwertsteuerModell calculateMissingValues(MehrwertsteuerModell mwstModell){
        float artikelPreis = mwstModell.getArtikelpreis();
        float steueranteil;
        float artMitSteuer = mwstModell.getArtMitSteuer();
        Mehrwertsteuerart mwstArt = mwstModell.getSteuertyp();
        if(artikelPreis == 0){
            artikelPreis = this.calculateArtikelpreis(artMitSteuer, mwstArt);
        }
        if(artMitSteuer == 0){
            artMitSteuer = this.calculateArtMitSteuer(artikelPreis, mwstArt);
        }
        steueranteil = this.calculateSteueranteil(artikelPreis, mwstArt);
        return new MehrwertsteuerModell(artikelPreis, steueranteil, artMitSteuer, mwstArt);
    }

    private MehrwertsteuerModell roundFloatingPointValues(MehrwertsteuerModell mwstModell){
        float roundedArtikelPreis = Math.round(mwstModell.getArtikelpreis()*100.0f)/100.0f;
        float roundedSteueranteil = Math.round(mwstModell.getSteueranteil()*100.0f)/100.0f;
        float roundedArtMitSteuer = Math.round(mwstModell.getArtMitSteuer()*100.0f)/100.0f;
        mwstModell.setArtikelpreis(roundedArtikelPreis);
        mwstModell.setSteueranteil(roundedSteueranteil);
        mwstModell.setArtMitSteuer(roundedArtMitSteuer);
        return mwstModell;
    }

    private boolean toMuchValuesAreZero(MehrwertsteuerModell mwstModell){
        float artikelPreis = mwstModell.getArtikelpreis();
        float artMitSteuer = mwstModell.getArtMitSteuer();
        return artikelPreis == 0 && artMitSteuer == 0;
    }

    private boolean steuerartIsNull(MehrwertsteuerModell mwstModell){
        return mwstModell.getSteuertyp() == null;
    }
}