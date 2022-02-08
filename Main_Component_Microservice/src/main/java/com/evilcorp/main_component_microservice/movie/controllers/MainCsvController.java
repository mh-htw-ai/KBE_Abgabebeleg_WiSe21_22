package com.evilcorp.main_component_microservice.movie.controllers;

import com.evilcorp.main_component_microservice.movie.services.csv_exporter_service.CsvExporterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/csv")
public class MainCsvController{

    private final CsvExporterService csvExporterService;

    @GetMapping("/export")
    public void exportCsvFile(){
        try{
            csvExporterService.exportRecentRatingsToCsv();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @PutMapping("/setup")
    public void setupSampleData() {

    }
}
