package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Pot {
    private final int maxVolume;
    private int currentVolume;
    final Object fullMonitor = new Object();
    final Object emptyMonitor = new Object();

    public Pot(int maxVolume) {
        this.maxVolume = maxVolume;
        this.currentVolume = 0;
    }

    public void waitFull() {
        synchronized (fullMonitor) {
            try {
                fullMonitor.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyFull() {
        synchronized (fullMonitor) {
            fullMonitor.notifyAll();
        }
    }

    public void waitEmpty() {
        synchronized (emptyMonitor) {
            try {
                emptyMonitor.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyEmpty() {
        synchronized (emptyMonitor) {
            emptyMonitor.notifyAll();
        }
    }

    public void fill() {
        if(isFull()) {
            System.out.println("Bee before wait");
            waitEmpty();
        }

        synchronized (this) {
            System.out.println(++currentVolume);
        }

        if(isFull()) {
            notifyFull();
        }
    }

    public synchronized boolean isFull() {
        return currentVolume == maxVolume;
    }

    public void eatHoney() {
        try {
            Thread.sleep((long)(Constants.DURATION * 9.5));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this) {
            currentVolume = 0;
        }

        notifyEmpty();
    }
}