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
        System.out.println("----------------------------------");

        if(areAmountsEqual()) {
            interruptAll();
        } else {
            clearFrequencies();
        }
    }

    private boolean areAmountsEqual() {
        int amountOfA = 0;
        int amountOfB = 0;

        for(Map<Character, Integer> frequency : charFrequencies) {
            if(frequency.containsKey('A')) {
                amountOfA += frequency.get('A');
            }

            if(frequency.containsKey('B')) {
                amountOfB += frequency.get('B');
            }
        }

        for(int i = 0; i < charFrequencies.length; i++) {
            int currentMapAAmount = charFrequencies[i].getOrDefault('A', 0);
            int currentMapBAmount = charFrequencies[i].getOrDefault('B', 0);

            if(amountOfA - currentMapAAmount == amountOfB - currentMapBAmount) {
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
