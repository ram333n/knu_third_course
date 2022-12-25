package org.example.transport;

public class Bus extends Transport {
    public Bus(String model) {
        super(model);
    }

    @Override
    public String toString() {
        return String.format("Bus %s", model);
    }
}
