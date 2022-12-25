package org.example;

import java.util.Random;

public final class UtilClass {
    public static final long DURATION = 2_000L;
    private static final Random random = new Random();
    private static final char[][] CHAR_PAIRS_FOR_REPLACING = {
            {'A', 'C'},
            {'C', 'A'},
            {'B', 'D'},
            {'D', 'B'}
    };

    public static char[] getCharsForReplacing() {
        return CHAR_PAIRS_FOR_REPLACING[random.nextInt(4)];
    }

    private UtilClass() {}
}
