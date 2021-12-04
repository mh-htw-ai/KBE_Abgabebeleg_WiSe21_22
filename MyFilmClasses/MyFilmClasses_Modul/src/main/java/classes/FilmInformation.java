package classes;


import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;


public class FilmInformation extends Film {

    @Getter
    @Setter
    private String titel;
    @Getter
    @Setter
    private Date erscheinungsdatum;
    @Getter
    @Setter
    private Time laufzeit;
    @Getter
    @Setter
    private double leihPreis;

    public FilmInformation(UUID uuid_Film) {
        super(uuid_Film);
    }

    public FilmInformation(UUID uuid_Film, String titel, Date erscheinungsdatum, Time laufzeit, double leihPreis) {
        super(uuid_Film);
        this.titel = titel;
        this.erscheinungsdatum = erscheinungsdatum;
        this.laufzeit = laufzeit;
        this.leihPreis = leihPreis;
    }



}
