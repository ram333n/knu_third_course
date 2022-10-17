package org.example;

import java.util.Queue;

public class Cashier implements Runnable {
    private final int id;
    private final Queue<Request> queue;
    private final Vault vault;

    public Cashier(int id, Queue<Request> queue, Vault vault) {
        this.id = id;
        this.queue = queue;
        this.vault = vault;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Constants.DURATION);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Request request = queue.poll();

            if (request == null) {
                continue;
            }

            if (request.isWithdrawal()) {
                vault.withdraw(request.getAmount(), id);
            } else {
                vault.add(request.getAmount(), id);
            }
        }
    }
}
