package com.evilcorp.mwst_microservice;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MehrwertsteuerModell {

    @DecimalMin(value="0.0", inclusive = true)
    private float Artikelpreis;

    @DecimalMin(value="0.0", inclusive = true)
    private float Steueranteil;

    @DecimalMin(value="0.0", inclusive = true)
    private float ArtMitSteuer;

    @NotNull
    private Mehrwertsteuerart Steuertyp;

    MehrwertsteuerModell(MehrwertsteuerModell mwstModell){
        this.Artikelpreis = mwstModell.Artikelpreis;
        this.Steueranteil = mwstModell.Steueranteil;
        this.ArtMitSteuer = mwstModell.ArtMitSteuer;
        this.Steuertyp = mwstModell.Steuertyp;
    }

    public MehrwertsteuerModell(float artikelpreis, float steueranteil, float artMitSteuer, Mehrwertsteuerart steuertyp) {
        this.Artikelpreis = artikelpreis;
        this.Steueranteil = steueranteil;
        this.ArtMitSteuer = artMitSteuer;
        this.Steuertyp = steuertyp;
    }

    public void calculateMissingValues(){
        if(this.Artikelpreis == 0) {
            this.calculateArtikelPreisFromArtMitSteuer();
        }else if(this.ArtMitSteuer == 0){
            this.calculateArtMitSteuerFromArtikelPreis();
        }
        BigDecimal ArtikelPreisBD = new BigDecimal(this.Artikelpreis).setScale(2, RoundingMode.HALF_UP);
        BigDecimal SteuerAnteilBD = new BigDecimal(this.Steueranteil).setScale(2, RoundingMode.HALF_UP);
        BigDecimal ArtMiSteuerBD = new BigDecimal(this.ArtMitSteuer).setScale(2, RoundingMode.HALF_UP);
        this.Artikelpreis = ArtikelPreisBD.floatValue();
        this.Steueranteil = SteuerAnteilBD.floatValue();
        this.ArtMitSteuer = ArtMiSteuerBD.floatValue();

    }

    public void calculateArtikelPreisFromArtMitSteuer(){
        this.Artikelpreis = this.ArtMitSteuer / (1+Steuertyp.getProzentsatz());
        this.calculateSteueranteilFromArtMitSteuer();
    }

    public void calculateSteueranteilFromArtMitSteuer(){
        this.Steueranteil = this.ArtMitSteuer - this.Artikelpreis;
    }

    public void calculateSteueranteilFromArtikelPreis(){
        this.Steueranteil = this.Artikelpreis * Steuertyp.getProzentsatz();
    }


    public void calculateArtMitSteuerFromArtikelPreis(){
        this.calculateSteueranteilFromArtikelPreis();
        this.ArtMitSteuer = this.Artikelpreis + this.Steueranteil;
    }

    public float getArtikelpreis() {
        return Artikelpreis;
    }

    public void setArtikelpreis(float artikelpreis) {
        Artikelpreis = artikelpreis;
    }

    public float getSteueranteil() {
        return Steueranteil;
    }

    public void setSteueranteil(float steueranteil) {
        Steueranteil = steueranteil;
    }

    public float getArtMitSteuer() {
        return ArtMitSteuer;
    }

    public void setArtMitSteuer(float artMitSteuer) {
        ArtMitSteuer = artMitSteuer;
    }

    public Mehrwertsteuerart getSteuertyp() {
        return Steuertyp;
    }

    public void setSteuertyp(Mehrwertsteuerart steuertyp) {
        Steuertyp = steuertyp;
    }
}
