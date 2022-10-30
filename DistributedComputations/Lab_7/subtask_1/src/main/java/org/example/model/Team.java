package org.example.model;

import java.util.UUID;

public class Team {
    private UUID id;
    private String name;
    private String country;

    public Team(UUID id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Team() {}

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
