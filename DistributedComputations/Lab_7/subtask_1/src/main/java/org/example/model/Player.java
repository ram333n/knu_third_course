package org.example.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Player {
    private UUID id;
    private UUID teamId;
    private String name;
    private BigDecimal price;

    public Player() {}

    public Player(UUID id, UUID teamId, String name, BigDecimal price) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void name(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
