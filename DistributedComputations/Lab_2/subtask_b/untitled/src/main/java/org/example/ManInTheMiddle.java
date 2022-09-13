package org.example;

import org.example.interfaces.IConsumer;
import org.example.interfaces.IProducer;

import java.util.concurrent.BlockingQueue;

public class ManInTheMiddle extends Thread implements IConsumer<Integer>, IProducer<Integer> {
    private final BlockingQueue<Integer> consumptionQueue;
    private final BlockingQueue<Integer> productionQueue;
    private final long interval;

    public ManInTheMiddle(BlockingQueue<Integer> consumptionQueue, BlockingQueue<Integer> productionQueue, long interval) {
        this.consumptionQueue = consumptionQueue;
        this.productionQueue = productionQueue;
        this.interval = interval;
    }

    @Override
    public Integer consume() throws InterruptedException {
        return consumptionQueue.take();
    }

    @Override
    public void produce(Integer product) throws InterruptedException {
        productionQueue.put(product);
    }

    @Override
    public boolean isPoisonPill(Integer message) {
        return message == -1;
    }

    @Override
    public void run() {
        while (true) {
            Integer consumedProduct = null;
            try {
                consumedProduct = consume();
                System.out.println("MITM consumed " + consumedProduct);

                if(isPoisonPill(consumedProduct)) {
                    produce(consumedProduct);
                    break;
                }

                sleep(interval);
                System.out.println("MITM processed " + consumedProduct);
                produce(consumedProduct);
                System.out.println("MITM produced " + consumedProduct);
            } catch (InterruptedException e) {
                System.out.println("Man in the middle interrupted");
            }
        }
    }
}
