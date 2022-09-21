package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Barbershop {
    private BlockingQueue<Customer> customers;

    public Barbershop() {
        this.customers = new LinkedBlockingQueue<>();
    }

    public void start() {
        Thread[] threads = new Thread[Constants.THREADS_COUNT];

        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Customer(this, Constants.DURATION * (i + 1) / threads.length, i));
        }

        Thread barber = new Thread(new Barber(this));

        for(Thread thread : threads) {
            thread.start();
        }

        barber.start();
    }

    public void addCustomer(Customer customer) throws InterruptedException {
        customers.put(customer);
    }

    public Customer takeCustomer() throws InterruptedException {
        return customers.take();
    }

    public int customersCount() {
        return customers.size();
    }
}