package org.example;

import java.util.Queue;
import java.util.Random;

public class Client implements Runnable {
    private final int id;
    private final Queue<Request> queue;
    private final Random random;

    public Client(int id, Queue<Request> queue) {
        this.id = id;
        this.queue = queue;
        this.random = new Random();
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Constants.DURATION - 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int amount = random.nextInt(80);
            boolean isWithdrawal = random.nextBoolean();

            Request request = new Request(amount, isWithdrawal);
            queue.add(request);
            System.out.printf("Client %d requested %s%n", id, request);
        }
    }
}
