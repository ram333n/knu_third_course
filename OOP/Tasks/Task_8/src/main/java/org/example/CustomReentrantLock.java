package org.example;

public class CustomReentrantLock {
    private int holdCount;
    private long currentThreadId;

    public CustomReentrantLock() {
        this.holdCount = 0;
    }

    public synchronized void lock() {
        if(holdCount == 0) {
            holdCount++;
            currentThreadId = Thread.currentThread().getId();
        } else {
            try {
                while(currentThreadId != Thread.currentThread().getId()) {
                    wait();
                    currentThreadId = Thread.currentThread().getId();
                }

                holdCount++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void unlock() {
        if(holdCount == 0) {
            throw new IllegalMonitorStateException("There isn't any locks in CustomReentrantLock");
        }

        holdCount--;

        if(holdCount == 0) {
            notifyAll();
        }
    }

    public synchronized boolean tryLock() {
        if(holdCount > 0) {
            return false;
        }

        lock();
        return true;
    }
}
