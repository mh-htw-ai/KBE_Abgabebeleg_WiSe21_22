package com.example.experimental_modul.customer_test;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor // Erstellt ein Konstruktor mit saemtlichen benoetigten Attributen
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository; //Mit Spring Boot JPA eine Verbindung zur Datenbank/Tabelle von Customer

    @GetMapping()
    @RequestMapping("/erreichbar")
    public ResponseEntity<String> getTest() {
        log.info("getTest() wird ausgeführt.");
        return new ResponseEntity<>("Service wurde erreicht", HttpStatus.OK);
    }

    @GetMapping(
            value="/{uuid}"
    )
    @RequestMapping("/{uuid}")
    @ResponseBody
    public ResponseEntity<String> getNewUUID(@PathVariable("uuid") String uuid) {
        log.info("getNewUUID() wird ausgeführt.");
        try {
            this.initiateCustomerDB();
            log.info("getNewUUID() gesuchte UUID: " + uuid);
            String ausgabe;
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
            this.initiateCustomerDB();
            UUID uuid1 = UUID.randomUUID();
            log.info("postNewCustomer(): new UUID = " + uuid1);
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


    /**
     * Funktion erstellt standartEintraege in die Datenbank
     */
    private void initiateCustomerDB (){
        long anzahl = this.customerRepository.count();
        if(anzahl == 0.0){
            log.info("Experimental: Datenbank erhält 3 Customer.");
            Customer cus1 = Customer.builder()
                    .uuid(UUID.randomUUID())
                    .name("Ingo_1")
                    .build();
            this.customerRepository.save(cus1);
            Customer cus2 = Customer.builder()
                    .uuid(UUID.randomUUID())
                    .name("Peter_2")
                    .build();
            this.customerRepository.save(cus2);
            Customer cus3 = Customer.builder()
                    .uuid(UUID.randomUUID())
                    .name("Ralf_3")
                    .build();
            this.customerRepository.save(cus3);
            log.info("Experimental: Datenbank hat 3 Customer erhalten.");
        }

    }
}
