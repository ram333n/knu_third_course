package org.example.interfaces;

import org.example.Producer;

public interface IProducer<T> {
    void produce(T product) throws InterruptedException;
}
