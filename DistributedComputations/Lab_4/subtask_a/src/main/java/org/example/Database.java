package org.example;

import org.example.util.Constants;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    private final List<User> users;
    private final String fileName;
    private final CustomReadWriteLock lock;

    public Database(String fileName) {
        this.fileName = fileName;
        this.users = new ArrayList<>();
        this.lock = new CustomReadWriteLock();

        loadFromFile();
        System.out.println(users);
    }

    public String getPhoneNumberByName(String name) throws InterruptedException {
        return findByPredicate((user -> Objects.equals(user.getFullName(), name)),
                User::getPhoneNumber);
    }

    public String getNameByPhoneNumber(String phoneNumber) throws InterruptedException {
        return findByPredicate((user -> Objects.equals(user.getPhoneNumber(), phoneNumber)),
                User::getFullName);
    }

    public List<User> delete(Predicate<User> predicate) throws InterruptedException {
        try {
            lock.writeLock();
            Thread.sleep(2 * Constants.DURATION);
            List<User> result = users.stream()
                            .filter(predicate)
                            .collect(Collectors.toList());

            if(users.removeIf(predicate)) {
                try(FileWriter writer = new FileWriter(fileName, false)) {
                    for(User user : users) {
                        writer.write(user.toString());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return result;
        } finally {
            lock.writeUnlock();
        }
    }

    public void write(User user) throws InterruptedException {
        lock.writeLock();
        Thread.sleep(2 * Constants.DURATION);
        try(FileWriter writer = new FileWriter(fileName, true)) {
            users.add(user);
            writer.append(user.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeUnlock();
        }
    }

    private String findByPredicate(Predicate<User> predicate,
                                   Function<User, String> mappingFunction) throws InterruptedException {
        try {
            lock.readLock();
            Thread.sleep(Constants.DURATION);
            Optional<User> found = users.stream()
                    .filter(predicate)
                    .findFirst();

            return found.map(mappingFunction).orElse(null);
        } finally {
            lock.readUnlock();
        }
    }

    private void loadFromFile() {
        try(Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while(scanner.hasNext()) {
                String firstName = scanner.next();
                String lastName = scanner.next();
                String patronymic = scanner.next();
                String phoneNumber = scanner.next();

                users.add(new User(firstName, lastName, patronymic, phoneNumber));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}