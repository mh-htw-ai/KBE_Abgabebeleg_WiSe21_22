package com.example.experimental_modul_gatewayclient;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@Slf4j
@RequestMapping("/employee")
public class TestServiceController {

    @GetMapping("/message")
    public String test(){
        return "(TestServiceController/)Hello JavaInUse:";
    }

}
