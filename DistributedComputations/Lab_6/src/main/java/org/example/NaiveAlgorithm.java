package org.example;

import mpi.MPI;

public final class NaiveAlgorithm {

    private NaiveAlgorithm() {}

    public static void calculate(String[] args, int matrixSize) {
        MPI.Init(args);

        int procRank = MPI.COMM_WORLD.Rank();

        Matrix matrixA = new Matrix(matrixSize, "A");
        Matrix matrixB = new Matrix(matrixSize, "B");
        Matrix matrixC = new Matrix(matrixSize, "C");
        long startTime = 0L;

        if (procRank == 0) {
            matrixA.fill(3);
            matrixB.fill(3);
            startTime = System.currentTimeMillis();
        }

        for (int i = 0; i < matrixA.columns; i++) {
            for (int j = 0; j < matrixB.rows; j++) {
                for (int k = 0; k < matrixA.rows; k++) {
                    matrixC.data[i * matrixA.columns + j] +=
                            matrixA.data[i * matrixA.columns + k] * matrixB.data[k * matrixB.columns + j];
                }
            }
        }

        if (procRank == 0) {
            System.out.printf("[Naive algorithm] matrixSize = %d, time = %d ms %n",
                    matrixSize, System.currentTimeMillis() - startTime);
        }

        MPI.Finalize();
    }
}