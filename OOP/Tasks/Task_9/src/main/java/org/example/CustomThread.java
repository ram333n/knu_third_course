package org.example;

public class CustomThread implements Runnable {
    private long duration;
    private CustomPhaser phaser;

    public CustomThread(CustomPhaser phaser, long duration) {
        this.phaser = phaser;
        this.duration = duration;
    }

    @Override
    public void run() {
        try {
            phaser.register();
            Thread.sleep(duration);
            System.out.println("Task "+Thread.currentThread().getId()+" arrived");
            phaser.arriveAndAwaitAdvance();

            System.out.println("Task "+Thread.currentThread().getId()+" arrived");
            phaser.arriveAndAwaitAdvance();
            Thread.sleep(duration);
            phaser.arriveAndDeregister();
            System.out.println("Task "+Thread.currentThread().getId()+" finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
