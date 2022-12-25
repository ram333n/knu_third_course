package org.example;

import org.example.interfaces.IMessage;
import org.example.interfaces.IProcessor;
import org.example.interfaces.IProducer;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Producer<T extends IMessage> extends Thread implements IProducer<T> {
    private final BlockingQueue<T> queue;
    private final List<T> data;
    private final IProcessor<T> processor;

    public Producer(BlockingQueue<T> queue, List<T> data, IProcessor<T> processor) {
        this.queue = queue;
        this.data = data;
        this.processor = processor;
    }

    @Override
    public void produce(T product) throws InterruptedException {
        System.out.println("Producer produced " + product.toString());
        queue.put(product);
    }

    @Override
    public void run() {
        for(int i = 0; i < data.size(); i++) {
            try {
                T product = data.get(i);
                if(i == data.size() - 1) {
                    product.makePoisonPill();
                }

                produce(product);
                processor.process(product);
            } catch (InterruptedException e) {
                System.out.println("Producer interrupted");
            }
        }
    }
}