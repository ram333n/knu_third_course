package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        List<Thread> bees = new ArrayList<>();
        Bear bear = new Bear();
        bear.setPosition(manager.getField());

        for(int i = 0; i < Constants.THREADS_COUNT; i++) {
            bees.add(new Bee(manager));
        }

        for(Thread bee : bees) {
            bee.start();
        }
    }
}