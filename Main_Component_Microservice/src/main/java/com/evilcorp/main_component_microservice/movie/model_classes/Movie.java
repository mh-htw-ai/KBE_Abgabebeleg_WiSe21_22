package com.evilcorp.main_component_microservice.movie.model_classes;


import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private UUID id;
    @NotBlank(message = "Title cannot be null/empty")
    @Size(min = 2, max = 40, message = "Title must be between 2 and 40 characters!")
    private String titel;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value="0.0", inclusive = false, message = "Price must be bigger than 0.0!")
    private float leihPreis;
    @NotBlank
    @Size(min = 10, max = 100, message = "Short description must be between 10 and 100 characters!")
    private String kurzbeschreibung;
    @Size(min = 30, max = 300, message = "Description must be between 10 and 100 characters!")
    private String beschreibung;

}
