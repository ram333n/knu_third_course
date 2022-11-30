package org.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        Thread t1 = new Thread(() -> queue.push(1));
        Thread t2 = new Thread(() -> queue.push(2));
        Thread t3 = new Thread(() -> queue.push(3));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        t1 = new Thread(() -> System.out.println(queue.pop()));
        t2 = new Thread(() -> System.out.println(queue.pop()));
        t3 = new Thread(() -> System.out.println(queue.pop()));

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Is null last: " + (queue.pop() == null ? "Yes" : "No"));
    }
}