package com.example.experimental_modul.customer_test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    //Hier koennen neben den Standards auch weitere Funktionen implementiert werden.
    // Es genuegt die richtige Funktionsbezeichnung fuer die "SQL-Query"-Information.
}
