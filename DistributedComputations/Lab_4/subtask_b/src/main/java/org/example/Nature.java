package org.example;

public class Nature implements Runnable {
    private final Garden garden;

    public Nature(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while(true) {
            garden.performNaturalPhenomena();
            System.out.println("Performed natural phenomena");
        }
    }
}
