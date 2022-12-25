package org.example;


public class Main {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 2000};

        for (int matrixSize : sizes) {
            NaiveAlgorithm.calculate(args, matrixSize);
            TapeAlgorithm.calculate(args, matrixSize);
            FoxAlgorithm.calculate(args, matrixSize);
            CannonAlgorithm.calculate(args, matrixSize);
        }
    }
}