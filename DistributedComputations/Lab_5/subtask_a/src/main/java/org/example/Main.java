package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(3, () -> System.out.println("aboba"));
        Thread t1 = new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread.sleep(200);

        Thread t2 = new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                barrier.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}