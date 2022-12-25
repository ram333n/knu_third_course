package org.example;

import static java.lang.Thread.sleep;

public class Bee implements Runnable {
    private final Pot pot;

    public Bee(Pot pot) {
        this.pot = pot;
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
