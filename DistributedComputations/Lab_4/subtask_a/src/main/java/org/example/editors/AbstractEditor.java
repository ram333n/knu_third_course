package org.example.editors;

import org.example.Database;

public abstract class AbstractEditor implements Runnable {
    protected final Database database;

    protected AbstractEditor(Database database) {
        this.database = database;
    }

    protected abstract boolean edit();

    @Override
    public void run() {
        while(true) {
            print();
        }
    }
}
