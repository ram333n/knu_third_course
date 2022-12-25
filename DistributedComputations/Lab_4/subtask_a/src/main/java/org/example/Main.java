package org.example;

import org.example.editors.AddEditor;
import org.example.editors.DeleteEditor;
import org.example.selectors.NameSelector;
import org.example.selectors.PhoneNumberSelector;
import org.example.util.Constants;
import org.example.util.PathBuilder;

import java.util.Objects;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Database database = new Database(PathBuilder.getPath(".", "src", "main", "java", "org", "example", "database.txt"));

        database.write(new User("Roman", "P", "I", "1234"));
        database.write(new User("Petro", "DD", "I", "1232"));
        database.write(new User("Mykola", "K", "R", "1237"));
        database.write(new User("Stepan", "I", "A", "1235"));
        database.write(new User("Viktor", "M", "V", "1229"));
        Random random = new Random();

        Thread nameSelector = new Thread(new NameSelector(database, "1234"));
        Thread phoneSelector = new Thread(new PhoneNumberSelector(database, "Roman P I"));

        Thread addEditor = new Thread(new AddEditor(database,
                () -> new User(Constants.RANDOM_NAMES[random.nextInt(Constants.RANDOM_NAMES.length)],
                        "R",
                        "R",
                        "123" + random.nextInt(10))));

        Thread deleteEditor = new Thread(new DeleteEditor(database,
                user -> Objects.equals(user.getPhoneNumber(), "123" + random.nextInt(10))));

        nameSelector.setPriority(Constants.SELECTOR_PRIORITY);
        nameSelector.start();
        Thread.sleep(random.nextLong(500L));

        phoneSelector.setPriority(Constants.SELECTOR_PRIORITY);
        phoneSelector.start();
        Thread.sleep(random.nextLong(500L));

        addEditor.start();
        Thread.sleep(random.nextLong(500L));

        deleteEditor.start();
        Thread.sleep(random.nextLong(500L));
    }
}