package org.example.editors;

import org.example.Database;
import org.example.User;

import java.util.List;
import java.util.function.Predicate;

public class DeleteEditor implements Runnable {
    private final Database database;
    private final Predicate<User> predicate;

    public DeleteEditor(Database database, Predicate<User> predicate) {
        this.database = database;
        this.predicate = predicate;
    }

    @Override
    public void run() {
        while(true) {
            try {
                List<User> deletedUsers = database.delete(predicate);

                if(!deletedUsers.isEmpty()) {
                    System.out.printf("%s deleted %d users : %n",
                            getClass().getSimpleName(),
                            deletedUsers.size());

                    for(User user : deletedUsers) {
                        System.out.print(user);
                    }
                } else {
                    System.out.printf("%s didn't perform delete operation%n", getClass().getSimpleName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
