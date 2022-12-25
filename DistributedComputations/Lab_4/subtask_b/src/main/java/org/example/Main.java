package org.example;

import org.example.monitors.ConsoleMonitor;
import org.example.monitors.FileMonitor;

public class Main {
    public static void main(String[] args) {
        Garden garden = new Garden(Constants.MATRIX_DIMENSION);

        Thread gardener = new Thread(new Gardener(garden));
        Thread nature = new Thread(new Nature(garden));
        Thread fileMonitor = new Thread(new FileMonitor(garden));
        Thread consoleMonitor = new Thread(new ConsoleMonitor(garden));

        try {
            gardener.start();
            Thread.sleep(Constants.DURATION / 4);

            nature.start();
            Thread.sleep(Constants.DURATION / 4);

            fileMonitor.start();
            Thread.sleep(Constants.DURATION / 4);

            consoleMonitor.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}