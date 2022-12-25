package org.example;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Vault {
    private final int capacity;
    private int currentAmount;
    private final ReadWriteLock lock;

    public Vault(int capacity) {
        this.capacity = capacity;
        this.currentAmount = 0;
        this.lock = new ReentrantReadWriteLock();
    }

    public void add(int money, int cashierId) {
        try {
            lock.writeLock().lock();
            currentAmount += money;
            System.out.printf("Manager %d added %d dollars. Storage amount: %d%n", cashierId, money, currentAmount);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void withdraw(int money, int cashierId) {
        try {
            lock.writeLock().lock();
            currentAmount -= money;
            System.out.printf("Manager %d withdrew %d dollars. Storage amount: %d%n", cashierId, money, currentAmount);
        } finally {
            lock.writeLock().unlock();
        }

    }

    public int getCurrentAmount() {
        try {
            lock.readLock().lock();
            return currentAmount;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull(int money) {
        try {
            lock.readLock().lock();
            return currentAmount + money >= capacity;
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        try {
            lock.readLock().lock();
            return currentAmount <= 100;
        } finally {
            lock.readLock().unlock();
        }
    }
}
