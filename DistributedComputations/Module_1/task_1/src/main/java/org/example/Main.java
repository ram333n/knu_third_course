package org.example;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) {
        Vault vault = new Vault(500);
        Queue<Request> queue = new ConcurrentLinkedQueue<>();

        for (int i = 1; i <= 4; i++) {
            new Thread(new Client(i, queue)).start();
            new Thread(new Cashier(i, queue, vault)).start();
        }

        new Thread(new Observer(vault)).start();
    }
}