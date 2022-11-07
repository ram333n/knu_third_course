package org.example;

import java.util.Random;

public class Matrix {
    static final Random random = new Random();
    final int[] data;
    final String name;
    final int rows;
    final int columns;

    public Matrix(int rows, int columns, String name) {
        this.rows = rows;
        this.columns = columns;
        this.data = new int[columns * rows];
        this.name = name;
    }

    public Matrix(int dimension, String name) {
        this.rows = dimension;
        this.columns = dimension;
        this.data = new int[columns * rows];
        this.name = name;
    }

    public void fill(int upperBound) {
        for (int i = 0; i < rows * columns; i++) {
            data[i] = random.nextInt(upperBound);
        }
    }
}