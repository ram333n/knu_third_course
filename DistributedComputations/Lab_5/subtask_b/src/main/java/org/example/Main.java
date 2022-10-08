package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) {
        Thread[] threads = new Thread[4];
        Map<Character, Integer>[] charFrequencies = new HashMap[threads.length];

        for(int i = 0; i < charFrequencies.length; i++) {
            charFrequencies[i] = new HashMap<>();
        }


        CyclicBarrier barrier = new CyclicBarrier(threads.length,
                                new CharCounter(charFrequencies, threads));

        threads[0] = new Thread(new StringModifier(charFrequencies[0],
                                                   new StringBuilder("ABACADBAD"),
                                                   barrier,
                                                   "First"));

        threads[1] = new Thread(new StringModifier(charFrequencies[1],
                                                   new StringBuilder("DBDADCCAA"),
                                                   barrier,
                                                   "Second"));

        threads[2] = new Thread(new StringModifier(charFrequencies[2],
                                                   new StringBuilder("BCBCBCBCB"),
                                                   barrier,
                                                   "Third"));

        threads[3] = new Thread(new StringModifier(charFrequencies[3],
                                                   new StringBuilder("ABCDBCBDC"),
                                                   barrier,
                                                   "Forth"));

        for(Thread thread : threads) {
            thread.start();
        }
    }
}