package classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class Film {

    @Getter
    protected UUID uuid_Film;
}
