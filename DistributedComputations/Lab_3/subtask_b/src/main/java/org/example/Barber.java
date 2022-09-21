package org.example;

import static java.lang.Thread.sleep;

public class Barber implements Runnable {
    Barbershop barbershop;

    public Barber(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    @Override
    public void run() {
        while(true) {
            try {
                if(barbershop.customersCount() == 0) {
                    System.out.println("Barber is sleeping...");
                }

                Customer currentCustomer = barbershop.takeCustomer();
                System.out.printf("Barber started cutting off %d%n", currentCustomer.getId());

                sleep(Constants.DURATION);

                System.out.printf("Barber finished cutting off %d%n", currentCustomer.getId());
                synchronized (currentCustomer) {
                    currentCustomer.notifyAll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
