package org.example;

import org.example.interfaces.IProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        final long INTERVAL = 5_000;
        List<Message> data = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            data.add(new Message(i));
        }

        BlockingQueue<Message> firstQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Message> secondQueue = new LinkedBlockingQueue<>();

        Thread producer = new Producer(firstQueue, data, (message) -> {
            try {
                sleep(INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread manInTheMiddle = new ManInTheMiddle(firstQueue, secondQueue, (message) -> {
            try {
                sleep(2 * INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Consumer(secondQueue, (message) -> {
            try {
                sleep(3 * INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        manInTheMiddle.start();
        consumer.start();
    }
}