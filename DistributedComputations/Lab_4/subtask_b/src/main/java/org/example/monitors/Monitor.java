package org.example.monitors;

import org.example.Garden;

public abstract class Monitor implements Runnable {
    protected final Garden garden;
    protected final String name;

    protected Monitor(Garden garden, String name) {
        this.garden = garden;
        this.name = name;
    }

    protected abstract void monitor();

    @Override
    public void run() {
        while(true) {
            monitor();
            System.out.printf("%s checked garden%n", name);
        }
    }
}
