package org.example;

import org.example.interfaces.IConsumer;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread implements IConsumer<Integer> {
    private final BlockingQueue<Integer> queue;
    private final long interval;

    public Consumer(BlockingQueue<Integer> queue, long interval) {
        this.queue = queue;
        this.interval = interval;
    }

    @Override
    public Integer consume() throws InterruptedException {
        return queue.take();
    }

    @Override
    public boolean isPoisonPill(Integer message) {
        return message == -1;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Integer consumedProduct = consume();
                System.out.println("Consumer received " + consumedProduct);

                if(isPoisonPill(consumedProduct)) {
                    break;
                }

                sleep(interval);
                System.out.println("Consumer processed " + consumedProduct);
            } catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }
        }
    }
}
