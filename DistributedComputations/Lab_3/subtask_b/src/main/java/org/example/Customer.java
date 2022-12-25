package org.example;

import static java.lang.Thread.sleep;

public class Customer implements Runnable {
    private final Barbershop barbershop;
    private final long frequency;
    private final int id;

    public Customer(Barbershop barbershop, long frequency, int id) {
        this.barbershop = barbershop;
        this.frequency = frequency;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep(frequency);

                int currentCount = barbershop.customersCount();

                if(currentCount > 0) {
                    System.out.printf("Customer %d fell asleep... He is %d in queue%n", id, currentCount + 1);
                } else {
                    System.out.printf("Customer %d is waiting his turn. He is %d in queue%n", id, currentCount + 1);
                }

                barbershop.addCustomer(this);

                synchronized (this) {
                    wait();
                }

                System.out.printf("Customer %d will come back in %d ms%n", id, frequency);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
