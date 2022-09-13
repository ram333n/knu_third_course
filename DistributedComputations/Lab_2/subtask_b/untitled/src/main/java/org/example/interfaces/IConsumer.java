package org.example.interfaces;

public interface IConsumer<T> {
    T consume() throws InterruptedException;
    boolean isPoisonPill(T message);
}
