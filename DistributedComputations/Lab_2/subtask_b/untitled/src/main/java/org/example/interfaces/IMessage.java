package org.example.interfaces;

public abstract class IMessage {
    protected boolean isPoisonPill;
    public boolean isPoisonPill() {
        return isPoisonPill;
    }

    public void makePoisonPill() {
        isPoisonPill = true;
    }
}