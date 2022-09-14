package org.example;

import java.security.SecureRandom;

public class Bear {
    private final int x;
    private final int y;

    public Bear() {
        SecureRandom random = new SecureRandom();
        this.x = random.nextInt(Constants.FIELD_SIZE);
        this.y = random.nextInt(Constants.FIELD_SIZE);
    }

    public void setPosition(boolean[][] field) {
        field[y][x] = true;
    }
}