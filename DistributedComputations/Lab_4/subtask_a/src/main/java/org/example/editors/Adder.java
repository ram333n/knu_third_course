package org.example.editors;

import org.example.Database;
import org.example.User;

import java.util.function.Supplier;

public class Adder extends AbstractEditor {
    private final Supplier<User> supplier;

    protected Adder(Database database, Supplier<User> supplier) {
        super(database);
        this.supplier = supplier;
    }

    @Override
    protected boolean edit() {
        try {
            database.write(supplier.get());
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void print() {
        if()
    }


}
