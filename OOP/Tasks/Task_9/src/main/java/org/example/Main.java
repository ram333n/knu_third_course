package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int threadsCount = 4;
        CustomPhaser phaser = new CustomPhaser();
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);

        executor.submit(new CustomThread(phaser, 1000L));
        executor.submit(new CustomThread(phaser, 2000L));
        executor.submit(new CustomThread(phaser, 3000L));

        executor.shutdown();
    }
}