package org.example;

import org.example.interfaces.IProducer;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread implements IProducer<Integer> {
    private final BlockingQueue<Integer> queue;
    private final List<Integer> data;
    private final long interval;

    public Producer(BlockingQueue<Integer> queue, List<Integer> data, long interval) {
        this.queue = queue;
        this.data = data;
        this.interval = interval;
    }

    @Override
    public void produce(Integer product) throws InterruptedException {
        queue.put(product);
    }

    @Override
    public void run() {
        for(Integer product : data) {
            try {
                produce(product);
                System.out.println("Producer produced " + product);
                sleep(interval);
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }

        try {
            produce(-1);
            System.out.println("Poison pilled!");
        } catch (InterruptedException e) {
            System.out.println("Producer interrupted in poison pill production");
        }
    }
}
