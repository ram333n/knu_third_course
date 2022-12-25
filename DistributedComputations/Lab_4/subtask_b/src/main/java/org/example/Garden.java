package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class Garden {
    private final boolean[][] field; //true if plant isn't withered
    private final ReadWriteLock lock;
    private final Random random;
    private final String fileName = "monitor_result.txt";

    public Garden(int dimension) {
        this.field = new boolean[dimension][dimension];
        this.lock = new ReentrantReadWriteLock();
        this.random = new Random();

        modifyField(random::nextBoolean);
    }

    public void waterPlants() {
        modifyField(() -> true);
    }

    public void performNaturalPhenomena() {
        modifyField(random::nextBoolean);
    }

    public void writeIntoFile() {
        lock.readLock().lock();

        try(FileWriter writer = new FileWriter(fileName, false);) {
            writer.write(monitorField());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void outputField() {
        lock.readLock().lock();
        System.out.println(monitorField());
        lock.readLock().unlock();
    }

    private void modifyField(Supplier<Boolean> supplier) {
        try {
            lock.writeLock().lock();

            for(int i = 0; i < field.length; i++) {
                for(int j = 0; j < field[0].length; j++) {
                    field[i][j] = supplier.get();
                }
            }

            Thread.sleep(Constants.DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private String monitorField() {
        StringBuilder builder = new StringBuilder();

        for (boolean[] row : field) {
            for (boolean cell : row) {
                builder.append(cell ? "H" : "W");
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}
