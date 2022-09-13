package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        final long INTERVAL = 5000;
        List<Integer> data = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            data.add(i);
        }

        BlockingQueue<Integer> firstQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> secondQueue = new LinkedBlockingQueue<>();

        Thread producer = new Producer(firstQueue, data, INTERVAL);
        Thread manInTheMiddle = new ManInTheMiddle(firstQueue, secondQueue, 2 * INTERVAL);
        Thread consumer = new Consumer(secondQueue, 3 * INTERVAL);

        producer.start();
        manInTheMiddle.start();
        consumer.start();
    }
}