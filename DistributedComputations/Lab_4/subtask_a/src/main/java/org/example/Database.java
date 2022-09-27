package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

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

    public String findByName(String name) throws InterruptedException {
        try {
            lock.readLock();
            Optional<User> found = users.stream()
                    .filter(current -> name.equals(current.getFullName()))
                    .findFirst();

            return found.map(User::getPhoneNumber).orElse(null);
        } finally {
            lock.readUnlock();
        }
    }

    public String findByPhoneNumber(String phoneNumber) throws InterruptedException {
        try {
            lock.readLock();
            Optional<User> found = users.stream()
                    .filter(current -> phoneNumber.equals(current.getPhoneNumber()))
                    .findFirst();

            lock.readUnlock();
            return found.map(User::getFullName).orElse(null);
        } finally {
            lock.readUnlock();
        }
    }

    public boolean delete(Predicate<User> predicate) throws InterruptedException {
        try {
            lock.writeLock();
            int sizeBefore = users.size();
            users.removeIf(predicate);
            boolean isModified = sizeBefore != users.size();

            if(isModified) {
                try(FileWriter writer = new FileWriter(fileName, false)) {
                    for(User user : users) {
                        writer.write(user.toString());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return isModified;
        } finally {
            lock.writeUnlock();
        }
    }

    public void write(User user) throws InterruptedException {
        lock.writeLock();
        try(FileWriter writer = new FileWriter(fileName, true);) {
            users.add(user);
            writer.append(user.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeUnlock();
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
