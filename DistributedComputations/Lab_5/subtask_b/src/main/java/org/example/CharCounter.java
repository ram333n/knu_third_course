package org.example;

import java.util.Map;

public class CharCounter implements Runnable {
    private final Map<Character, Integer>[] charFrequencies;
    private final Thread[] threads;

    public CharCounter(Map<Character, Integer>[] charFrequencies, Thread[] threads) {
        this.charFrequencies = charFrequencies;
        this.threads = threads;
    }

    @Override
    public void run() {
        if(areAmountsEqual()) {
            interruptAll();
        } else {
            clearFrequencies();
        }

        System.out.println("----------------------------------");
    }

    private boolean areAmountsEqual() {
        int amountOfA = 0;
        int amountOfB = 0;

        for(int i = 0; i < charFrequencies.length; i++) {
            System.out.printf("%d) : %s%n", i + 1, charFrequencies[i]);
            amountOfA += charFrequencies[i].getOrDefault('A', 0);
            amountOfB += charFrequencies[i].getOrDefault('B', 0);
        }

        if(amountOfA == amountOfB) {
            System.out.printf("Four threads have the same count of A(%d) and B(%d). Terminate...%n", amountOfA, amountOfB);
            return true;
        }

        for(int i = 0; i < charFrequencies.length; i++) {
            int currentMapAAmount = charFrequencies[i].getOrDefault('A', 0);
            int currentMapBAmount = charFrequencies[i].getOrDefault('B', 0);

            int partialSumOfA = amountOfA - currentMapAAmount;
            int partialSumOfB = amountOfB - currentMapBAmount;

            if(partialSumOfA == partialSumOfB) {
                System.out.printf("Three threads have the same count of A(%d) and B(%d). Terminate...%n", partialSumOfA, partialSumOfB);
                return true;
            }
        }

        return false;
    }

    private void interruptAll() {
        for(Thread thread : threads) {
            thread.interrupt();
        }
    }

    private void clearFrequencies() {
        for(Map<Character, Integer> frequency : charFrequencies) {
            frequency.clear();
        }
    }
}
