package org.example;

public class Bear implements Runnable {
    private final Pot pot;

    public Bear(Pot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while(true) {
            pot.waitFull();
            System.out.println("Bear woke up");
            pot.eatHoney();
            System.out.println("Bear ate honey");
        }
    }
}
