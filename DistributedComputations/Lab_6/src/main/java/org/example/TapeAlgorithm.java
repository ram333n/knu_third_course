package org.example;

import mpi.MPI;

public final class TapeAlgorithm {
    private TapeAlgorithm() {}

    public static void calculate(String[] args, int matrixSize) {
        MPI.Init(args);
        int procRank = MPI.COMM_WORLD.Rank();

        int procNum = MPI.COMM_WORLD.Size();

        Matrix matrixA = new Matrix(matrixSize, "A");
        Matrix matrixB = new Matrix(matrixSize, "B");
        Matrix matrixC = new Matrix(matrixSize, "C");
        long startTime = 0L;

        if (procRank == 0) {
            matrixB.fill(4);
            matrixA.fill(4);
            startTime = System.currentTimeMillis();
        }

        int lineHeight = matrixSize / procNum;

        int[] bufferA = new int[lineHeight * matrixSize];
        int[] bufferB = new int[lineHeight * matrixSize];
        int[] bufferC = new int[lineHeight * matrixSize];

        MPI.COMM_WORLD.Scatter(matrixA.data,
                0,
                lineHeight * matrixSize,
                MPI.INT,
                bufferA,
                0,
                lineHeight * matrixSize,
                MPI.INT,
                0
        );

        MPI.COMM_WORLD.Scatter(
                matrixB.data,
                0,
                lineHeight * matrixSize,
                MPI.INT,
                bufferB,
                0,
                lineHeight * matrixSize,
                MPI.INT,
                0
        );

        int nextProc = (procRank + 1) % procNum;
        int prevProc = procRank - 1;

        if (prevProc < 0) {
            prevProc = procNum - 1;
        }

        int prevDataNum = procRank;

        for (int p = 0; p < procNum; p++) {
            for (int i = 0; i < lineHeight; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    for (int k = 0; k < lineHeight; k++) {
                        bufferC[i * matrixSize + j] += bufferA[prevDataNum * lineHeight + i * matrixSize + k] * bufferB[k * matrixSize + j];
                    }
                }
            }

            prevDataNum -= 1;

            if (prevDataNum < 0) {
                prevDataNum = procNum - 1;
            }

            MPI.COMM_WORLD.Sendrecv_replace(
                    bufferB,
                    0,
                    lineHeight * matrixSize,
                    MPI.INT,
                    nextProc,
                    0,
                    prevProc,
                    0
            );
        }

        MPI.COMM_WORLD.Gather(
              bufferC,
              0,
              lineHeight * matrixSize,
              MPI.INT, matrixC.data,
              0,
              lineHeight * matrixSize,
              MPI.INT,
              0
        );

        if (procRank == 0) {
            System.out.printf("[Tape algorithm] matrixSize = %d, time = %d ms %n",
                    matrixSize, System.currentTimeMillis() - startTime);
        }

        MPI.Finalize();
    }
}