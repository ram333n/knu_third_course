package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[UtilClass.PARTS];
        int[] recruits = UtilClass.generateRecruits();
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(UtilClass.PARTS);

        RecruitsPart.fillFinishedArray(UtilClass.PARTS);

        for(int i = 0; i < threads.length; i++){
            threads[i] = new Thread(new RecruitsPart(recruits, barrier, i, i * 50, (i + 1) * 50));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Result: %s%n", Arrays.toString(recruits));
    }
}