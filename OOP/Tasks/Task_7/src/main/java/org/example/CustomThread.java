package org.example;

public class CustomThread extends Thread {
    private long duration;
    private int number;
    private final CustomCyclicBarrier barrier;

    public CustomThread(CustomCyclicBarrier barrier, long duration, int number) {
        this.barrier = barrier;
        this.duration = duration;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration);
            System.out.printf("Thread %d waited for %d ms %n", number, duration);
            barrier.await();
            System.out.println("Some thread action after barrier");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
