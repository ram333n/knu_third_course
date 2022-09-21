package org.example;

import static java.lang.Thread.sleep;

public class Bee implements Runnable {
    private final Pot pot;
    private final Bear bear;

    public Bee(Pot pot, Bear bear) {
        this.pot = pot;
        this.bear = bear;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(Constants.DURATION);
                pot.fill();
                System.out.printf("Bee with id %d filled pot%n", Thread.currentThread().getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
