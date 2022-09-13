package org.example;

import org.example.interfaces.IConsumer;
import org.example.interfaces.IMessage;
import org.example.interfaces.IProcessor;
import org.example.interfaces.IProducer;

import java.util.concurrent.BlockingQueue;

public class ManInTheMiddle<T extends IMessage> extends Thread implements IConsumer<T>, IProducer<T> {
    private final BlockingQueue<T> consumptionQueue;
    private final BlockingQueue<T> productionQueue;
    private final IProcessor<T> processor;

    public ManInTheMiddle(BlockingQueue<T> consumptionQueue, BlockingQueue<T> productionQueue, IProcessor<T> processor) {
        this.consumptionQueue = consumptionQueue;
        this.productionQueue = productionQueue;
        this.processor = processor;
    }

    @Override
    public T consume() throws InterruptedException {
        T consumedProduct = consumptionQueue.take();
        System.out.println("MITM consumed " + consumedProduct.toString());
        return consumedProduct;
    }

    @Override
    public void produce(T product) throws InterruptedException {
        System.out.println("MITM produced " + product.toString());
        productionQueue.put(product);
    }

    @Override
    public void run() {
        while (true) {
            try {
                T consumedProduct = consume();
                processor.process(consumedProduct);
                produce(consumedProduct);

                if(consumedProduct.isPoisonPill()) {
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println("Man in the middle interrupted");
            }
        }
    }
}
