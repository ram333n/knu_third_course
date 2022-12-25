package org.example.interfaces;

public interface IConsumer<T extends IMessage>  {
    T consume() throws InterruptedException;
}
