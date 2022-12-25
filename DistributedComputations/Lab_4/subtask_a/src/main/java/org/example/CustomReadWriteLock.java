package org.example;

public class CustomReadWriteLock {
    private int readersCount = 0;
    private int writersCount = 0;
    private int writeRequests = 0;

    public CustomReadWriteLock() {
        this.readersCount = 0;
        this.writersCount = 0;
        this.writeRequests = 0;
    }

    public synchronized void readLock() throws InterruptedException {
        while(writersCount > 0 || writeRequests > 0) {
            wait();
        }

        readersCount++;
    }

    public synchronized void readUnlock() {
        readersCount--;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        writeRequests++;

        while(writersCount > 0 || readersCount > 0) {
            wait();
        }

        writeRequests--;
        writersCount++;
    }

    public synchronized void writeUnlock() {
        writersCount--;
        notifyAll();
    }
}
