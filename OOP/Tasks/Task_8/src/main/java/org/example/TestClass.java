package org.example;

public class TestClass {
    private int counter;
    private CustomReentrantLock lock = new CustomReentrantLock();

    public TestClass() {
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void increment() {
        lock.lock();
        counter++;
        printValue();
        lock.unlock();
    }

    private void printValue() {
        lock.lock();
        System.out.println(counter);
        lock.unlock();
    }
}
