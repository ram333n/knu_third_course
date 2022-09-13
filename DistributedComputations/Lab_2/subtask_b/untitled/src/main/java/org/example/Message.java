package org.example;

import org.example.interfaces.IMessage;

public class Message extends IMessage {
    private final Integer number;

    public Message(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
