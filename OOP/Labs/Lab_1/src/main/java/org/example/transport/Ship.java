package org.example.transport;

public class Ship extends Transport {
    public Ship(String model) {
        super(model);
    }

    @Override
    public String toString() {
        return String.format("Ship %s", model);
    }
}
