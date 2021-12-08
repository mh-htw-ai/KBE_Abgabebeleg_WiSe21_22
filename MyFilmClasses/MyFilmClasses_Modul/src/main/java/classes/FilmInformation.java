package classes;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@DiscriminatorValue("FilmInformation")
@SuperBuilder
@AllArgsConstructor
public class FilmInformation extends Film {

    private static final Logger log = LoggerFactory.getLogger(FilmInformation.class);

    @Getter @Setter @Column
    protected String titel;

    @Getter @Setter @Column
    protected Date erscheinungsdatum;

    @Getter @Setter @Column
    protected Time laufzeit;

    @Getter @Setter @Column
    protected double leihPreis;

    public FilmInformation(
            UUID uuid_Film) {
        super(uuid_Film);
    }

    public FilmInformation(
            UUID uuid_Film
            , String titel
            , Date erscheinungsdatum
            , Time laufzeit
            , double leihPreis) {
        super(uuid_Film);
        this.titel = titel;
        this.erscheinungsdatum = erscheinungsdatum;
        this.laufzeit = laufzeit;
        this.leihPreis = leihPreis;
    }

    public FilmInformation() {
        super();
    }
}
