package org.example;

public class Main {
    public static void main(String[] args) {
        Pot pot = new Pot(Constants.MAX_VOLUME);
        Bear bear = new Bear(pot);

        Thread bearThread = new Thread(bear);
        Thread[] bees = new Thread[Constants.BEES_COUNT];

        for(int i = 0; i < bees.length; i++) {
            bees[i] = new Thread(new Bee(pot));
        }

        bearThread.start();

        for(Thread thread : bees) {
            thread.start();
        }
    }
}