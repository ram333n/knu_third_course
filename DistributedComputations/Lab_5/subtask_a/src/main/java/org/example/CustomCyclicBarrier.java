package org.example;

public class CustomCyclicBarrier {
    private int threadsCount;
    private int threadsAwait;
    private final Runnable barrierAction;

    public CustomCyclicBarrier(int threadsCount, Runnable barrierAction) {
        this.threadsCount = threadsCount;
        this.threadsAwait = threadsCount;
        this.barrierAction = barrierAction;
    }

    public CustomCyclicBarrier(int threadsCount) {
        this(threadsCount, null);
    }

    public synchronized void await() throws InterruptedException {
        threadsAwait--;

        if(threadsAwait > 0) {
            wait();
        } else {
            threadsAwait = threadsCount;
            notifyAll();

            if(barrierAction != null) {
                barrierAction.run();
            }
        }
    }
}
