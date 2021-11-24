package com.example.test_modul.controller;


import com.example.test_modul.entity.Customer;
import com.example.test_modul.repository.CustomerRepository;
import com.example.test_modul.uuidtest.Test_01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor // Erstellt ein Konstruktor mit saemtlichen benoetigten Attributen
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(Test_01.class);

    private final CustomerRepository customerRepository; //Mit Spring Boot JPA eine Verbindung zur Datenbank/Tabelle von Customer

    @GetMapping()
    @RequestMapping("/erreichbar")
    public ResponseEntity<String> getTest() {
        log.info("getTest() wird ausgeführt.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
            value="/{uuid}"
    )
    @RequestMapping("/{uuid}")
    @ResponseBody
    public ResponseEntity<String> getNewUUID(@PathVariable("uuid") String uuid) {
        log.info("getNewUUID() wird ausgeführt.");
        try {
            log.info("getNewUUID() gesuchte UUID: " + uuid);
            String ausgabe = "";
            UUID uuid1 = UUID.fromString(uuid);
            log.info("getNewUUID() uuid1 wurde erstellt.");
            Customer customer = customerRepository.getById(uuid1);
            log.info("Costumer wurde aus der Datenbank geholt.");
            ausgabe = "UUID = " + customer.getUuid() + System.getProperty("line.separator");
            ausgabe += "Name = " + customer.getName() + System.getProperty("line.separator");
            return new ResponseEntity<>(ausgabe, HttpStatus.OK);
        }
        catch (Exception ex){
            log.warn("getNewUUID() hat eine Exception geworfen.");
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Erstellung ein neuen DB-Eintrag mit einem Body
     * @param body String
     * @return UUID des neuen abgespeicherten Objekts
     */
    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<String> postNewCustomer(@RequestBody String body){
        log.info("postNewCustomer() wird ausgeführt.");
        try {
            log.info("postNewCustomer(): Body=" + body);
            UUID uuid1 = UUID.randomUUID();
            log.info("postNewCustomer(): new UUID = " + uuid1.toString());
            Customer customer = new Customer(uuid1, body);
            log.info("postNewCustomer(): Customer wurde erstellt.");
            customerRepository.save(customer);
            log.info("postNewCustomer(): Customer wurde gespeichert.");
            return new ResponseEntity<>(uuid1.toString(), HttpStatus.OK);
        }
        catch (Exception ex){
            log.warn("postNewCustomer() hat eine Exception geworfen.");
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
