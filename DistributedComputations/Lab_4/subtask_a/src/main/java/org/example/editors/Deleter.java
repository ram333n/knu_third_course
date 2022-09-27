package org.example.editors;

import org.example.Database;
import org.example.User;

import java.util.function.Predicate;

public class Deleter extends AbstractEditor {
    private final Predicate<User> predicate;

    public Deleter(Database database, Predicate<User> predicate) {
        super(database);
        this.predicate = predicate;
    }

    @Override
    protected boolean edit() {
        try {
            return database.delete(predicate);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
