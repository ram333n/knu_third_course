package org.example;

public class CustomTask implements Runnable {
    private final String name;
    private final long duration;

    public CustomTask(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration);
            System.out.println("Thread: " + name + " finished its task");
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }
    }
}
