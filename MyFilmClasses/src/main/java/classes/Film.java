package classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

@Builder
@AllArgsConstructor
public class Film {

    private static final Logger log = LoggerFactory.getLogger(Film.class);

    @Getter
    protected UUID uuid_Film;

    /**
     * Funktion zum Testen
     */
    public void testLogAusgabe(){
        log.info("testLogAusgabe() wurde ausgeführt.");
    }


    /**
     * Funktion zum Testen
     */
    public void testLogAusgabe2(){
        log.info("testLogAusgabe() wurde ausgeführt.");
    }
}
