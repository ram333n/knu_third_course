package org.example;

public class CustomCyclicBarrier {
    private int threadsCount;
    private int awaitCount;
    private final Runnable barrierAction;

    public CustomCyclicBarrier(int threadsCount, Runnable barrierAction) {
        this.threadsCount = threadsCount;
        this.awaitCount = threadsCount;
        this.barrierAction = barrierAction;
    }

    public synchronized void await() throws InterruptedException {
        awaitCount--;

        if (awaitCount > 0) {
            wait();
        } else {
            awaitCount = threadsCount;
            barrierAction.run();
            notifyAll();
        }
    }
}
