package org.example.transport;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Transport {
    protected String model;

    @Override
    public String toString() {
        return "Transport{" +
                "model='" + model + '\'' +
                '}';
    }
}