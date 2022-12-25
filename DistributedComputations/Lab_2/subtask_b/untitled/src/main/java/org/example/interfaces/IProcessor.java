package org.example.interfaces;

@FunctionalInterface
public interface IProcessor<T extends IMessage> {
    void process(IMessage message);
}