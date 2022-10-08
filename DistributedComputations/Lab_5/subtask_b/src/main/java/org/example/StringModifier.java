package org.example;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StringModifier implements Runnable {
    private final Map<Character, Integer> frequency;
    private final StringBuilder str;
    private final CyclicBarrier barrier;
    private final String name;

    public StringModifier(Map<Character, Integer> frequency,
                          StringBuilder str,
                          CyclicBarrier barrier,
                          String name) {
        this.frequency = frequency;
        this.str = str;
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                final char[] pair = UtilClass.getCharsForReplacing();
                final char source = pair[0];
                final char target = pair[1];
                replaceChars(source, target);
                Thread.sleep(UtilClass.DURATION);
                System.out.printf("%s replaced chars(%s-%s), value : %s%n", name, source, target, str);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void replaceChars(char source, char target) {
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == source) {
                str.setCharAt(i, target);
            }

            if(target == 'A' || target == 'B') {
                frequency.merge(str.charAt(i), 1, Integer::sum);
            }
        }
    }
}
