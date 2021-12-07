package com.evilcorp.main_component_microservice;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class User {

    @Id
    private UUID id;
    private String name, lastname;

    public User(UUID id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
