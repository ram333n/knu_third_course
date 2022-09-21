package org.example;

import static java.lang.Thread.sleep;

public class Bear implements Runnable {
    private final Pot pot;

    public Bear(Pot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while(true) {
            try {
                pot.eatHoney();
                sleep(Constants.DURATION);
                System.out.println("Bear ate honey");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
