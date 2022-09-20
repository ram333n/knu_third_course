package org.example;

import static java.lang.Thread.sleep;

public class Bear implements Runnable {
    private final Pot pot;

    public Bear(Pot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while(Pot.capacity > 0) {
            try {
                pot.eatHoney();
                Pot.capacity--;
                sleep(Constants.DURATION);
                System.out.println("Bear ate honey");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
