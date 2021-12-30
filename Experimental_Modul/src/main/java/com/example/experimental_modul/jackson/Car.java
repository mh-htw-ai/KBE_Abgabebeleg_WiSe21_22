package com.example.experimental_modul.jackson;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Getter @Setter
    private String color;
    @Getter @Setter
    private String type;

}