package org.example;

public class Manager {
    private final boolean[][] field;
    private volatile boolean bearFound;
    private int nextSection;

    public Manager() {
        this.field = new boolean[Constants.FIELD_SIZE][Constants.FIELD_SIZE];
        bearFound = false;
        nextSection = 0;
    }

    public synchronized int getTask() {
        return bearFound ? -1 : nextSection++ % Constants.FIELD_SIZE;
    }

    public boolean[][] getField() {
        return field;
    }

    public void bearFound() {
        bearFound = true;
    }
}