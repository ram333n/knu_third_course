package org.example;

import java.util.Random;

public final class UtilClass {
    private UtilClass() {}

    public static final int RECRUITS_COUNT = 150;
    public static final int PARTS = 3;

    public static int[] generateRecruits() {
        int[] result = new int[RECRUITS_COUNT];
        Random random = new Random();

        for(int i = 0; i < result.length; i++) {
            if(random.nextBoolean()) {
                result[i] = 1;
            } else {
                result[i] = -1;
            }
        }

        return result;
    }
}
