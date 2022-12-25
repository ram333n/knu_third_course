package org.example;

public class Gardener implements Runnable {
    private final Garden garden;

    public Gardener(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while(true) {
            garden.waterPlants();
            System.out.println("Gardener watered plants");
        }
    }
}
