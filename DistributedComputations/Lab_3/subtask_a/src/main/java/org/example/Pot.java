package org.example;

public class Pot {
    public static int capacity = Constants.EATING_COUNT;
    private final int maxVolume;
    private int currentVolume;

    public Pot(int maxVolume) {
        this.maxVolume = maxVolume;
        this.currentVolume = 0;
    }

    public synchronized void fill() {
        if(isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        currentVolume++;

        if(isFull()) {
            notifyAll();
        }
    }

    public boolean isFull() {
        return currentVolume == maxVolume;
    }

    public synchronized void eatHoney() {
        if(!isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        currentVolume = 0;

        notifyAll();
    }
}