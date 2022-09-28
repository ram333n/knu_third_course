package org.example.editors;

import org.example.Database;
import org.example.User;
import org.example.util.Constants;

import java.util.function.Supplier;

public class AddEditor implements Runnable {
    private final Database database;
    private final Supplier<User> supplier;

    public AddEditor(Database database, Supplier<User> supplier) {
        this.database = database;
        this.supplier = supplier;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(2 * Constants.DURATION);
                User toAdd = supplier.get();
                database.write(toAdd);
                System.out.printf("%s wrote user(full name : %s, phone : %s)%n",
                        getClass().getSimpleName(),
                        toAdd.getFullName(),
                        toAdd.getPhoneNumber());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
