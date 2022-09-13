package org.example.interfaces;

import org.example.Producer;

public interface IProducer<T extends IMessage> {
    void produce(T product) throws InterruptedException;
}
