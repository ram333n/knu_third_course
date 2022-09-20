package org.example.transport;

public class Plane extends Transport {
    public Plane(String model) {
        super(model);
    }

    @Override
    public String toString() {
        return String.format("Plane %s", model);
    }
}
