package org.example;

public class Main {

    public static void main(String[] args) {
        int threadsCount = 5;
        Thread[] threads = new Thread[threadsCount];
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(threadsCount, () -> System.out.println("Finish"));
        long minDuration = 1500L;

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new CustomThread(barrier, (i + 1) * minDuration, i);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}