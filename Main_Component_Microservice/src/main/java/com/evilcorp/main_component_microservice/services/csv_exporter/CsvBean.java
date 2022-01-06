package com.evilcorp.main_component_microservice.services.csv_exporter;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CsvBean {

    @CsvBindByName(column = "UUID")
    private UUID movieId;
    @CsvBindByName(column = "Gesamtwertung")
    private int ratingUserCount;
    @CsvBindByName(column = "Zuschauerzahl")
    private int averageRating;

}
