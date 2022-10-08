package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[4];
        Map<Character, Integer>[] charFrequencies = new HashMap[threads.length];
        StringBuilder[] testStrings =  {
                new StringBuilder("ABACADBAD"),
                new StringBuilder("DBDADCCAA"),
                new StringBuilder("BCBCBCBCB"),
                new StringBuilder("ABCDBCBDC")
        };

        for(int i = 0; i < charFrequencies.length; i++) {
            System.out.printf("%s will modify %s%n", i + 1 + ")", testStrings[i]);
            charFrequencies[i] = new HashMap<>();
        }

        System.out.println("-------------------------------");

        CyclicBarrier barrier = new CyclicBarrier(threads.length,
                                new CharCounter(charFrequencies, threads));

        for(int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new StringModifier(charFrequencies[i],
                                    testStrings[i],
                                    barrier,
                                    i + 1 + ")"));

            if(i == 1) { //just to delay one of threads
                Thread.sleep(200L);
            }
        }

        for(Thread thread : threads) {
            thread.start();
        }
    }
}