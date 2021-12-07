package classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SuperBuilder
@AllArgsConstructor
@DiscriminatorColumn(name = "type")
public class Film {

    private static final Logger log = LoggerFactory.getLogger(Film.class);

    @Id @Getter @Setter
    protected UUID uuid_Film;

    public Film() {
        this.uuid_Film = UUID.randomUUID();
    }

}
