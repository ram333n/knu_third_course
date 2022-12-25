package org.example;

import mpi.Cartcomm;
import mpi.MPI;

import java.util.Arrays;

public final class FoxAlgorithm {
    private FoxAlgorithm() {}

    private static int[] gridCoords = new int[2];
    private static Cartcomm colComm;
    private static Cartcomm rowComm;

    private static void matrixScatter(int[] matrix, int[] matrixBlock, int matrixSize, int blockSize) {
        int[] matrixRow = new int[blockSize * matrixSize];

        if (gridCoords[1] == 0){
            colComm.Scatter(
                    matrix,
                    0,
                    blockSize * matrixSize,
                    MPI.INT,
                    matrixRow,
                    0,
                    blockSize * matrixSize,
                    MPI.INT,
                    0
            );
        }

        for (int i = 0; i < blockSize; i++) {
            int[] subRow = Arrays.copyOfRange(matrixRow, i * matrixSize, matrixRow.length);
            int[] subRowRes = new int[blockSize];

            rowComm.Scatter(subRow,
                    0,
                    blockSize,
                    MPI.INT,
                    subRowRes,
                    0,
                    blockSize,
                    MPI.INT,
                    0
            );

            System.arraycopy(subRowRes, 0, matrixBlock, i * blockSize, blockSize);
        }
    }

    public static void calculate(String[] args, int matrixSize) {
        MPI.Init(args);
        int procRank = MPI.COMM_WORLD.Rank();
        int procNum = MPI.COMM_WORLD.Size();

        int gridSize = (int) Math.sqrt(procNum);

        if (procNum != gridSize * gridSize) {
            if (procRank == 0) {
                System.out.printf("[Fox algorithm] %d x %d, 0 ms (procNum != gridSize * gridSize)%n", matrixSize, matrixSize);
            }

            MPI.Finalize();
            return;
        }

        Cartcomm gridComm;

        int blockSize = matrixSize / gridSize;

        Matrix matrixA = new Matrix(matrixSize, "A");
        Matrix matrixB = new Matrix(matrixSize, "B");
        Matrix matrixC = new Matrix(matrixSize, "C");

        int[] blockA = new int[blockSize * blockSize];
        int[] blockB = new int[blockSize * blockSize];
        int[] blockC = new int[blockSize * blockSize];
        int[] tempABlock = new int[blockSize * blockSize];

        long startTime = 0L;
        if (procRank == 0) {
            matrixA.fill(5);
            matrixB.fill(5);
            startTime = System.currentTimeMillis();
        }

        boolean[] subdims = new boolean[2];

        gridComm = MPI.COMM_WORLD.Create_cart(new int[]{gridSize, gridSize}, new boolean[]{false, false}, true);

        gridCoords = gridComm.Coords(procRank);

        subdims[1] = true;
        rowComm = gridComm.Sub(subdims);

        subdims[0] = true;
        subdims[1] = false;
        colComm = gridComm.Sub(subdims);

        matrixScatter(matrixA.data, tempABlock, matrixSize, blockSize);
        matrixScatter(matrixB.data, blockB, matrixSize, blockSize);

        for (int iter = 0; iter < gridSize; iter++) {

            int pivot = (gridCoords[0] + iter) % gridSize;

            if (gridCoords[1] == pivot) {
                if (blockSize * blockSize >= 0) {
                    System.arraycopy(tempABlock, 0, blockA, 0, blockSize * blockSize);
                }
            }

            rowComm.Bcast(blockA, 0, blockSize * blockSize, MPI.INT, pivot);

            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    for (int k = 0; k < blockSize; k++) {
                        blockC[i * blockSize + j] += blockA[i * blockSize + k] * blockB[k * blockSize + j];
                    }
                }
            }

            int nextProc = gridCoords[0] + 1;

            if (gridCoords[0] == gridSize - 1) {
                nextProc = 0;
            }

            int prevProc = gridCoords[0] - 1;

            if (gridCoords[0] == 0) {
                prevProc = gridSize - 1;
            }

            colComm.Sendrecv_replace(
                    blockB,
                    0,
                    blockSize * blockSize,
                    MPI.INT,
                    nextProc,
                    0,
                    prevProc,
                    0
            );
        }

        int[] resultRow = new int[matrixSize * blockSize];
        for (int i = 0; i < blockSize; i++) {
            int[] subRow = Arrays.copyOfRange(blockC, i * blockSize, blockC.length);
            int[] subRowRes = new int[gridSize * blockSize];

            rowComm.Gather(
                    subRow,
                    0,
                    blockSize,
                    MPI.INT,
                    subRowRes,
                    0,
                    blockSize,
                    MPI.INT,
                    0
            );

            if (gridSize * blockSize >= 0) {
                System.arraycopy(subRowRes, 0, resultRow, i * matrixSize, gridSize * blockSize);
            }
        }

        if (gridCoords[1] == 0) {
            colComm.Gather(
                    resultRow,
                    0,
                    blockSize * matrixSize,
                    MPI.INT,
                    matrixC.data, 0, blockSize * matrixSize, MPI.INT, 0);
        }

        if (procRank == 0) {
            System.out.printf("[Fox algorithm] matrixSize = %d, time = %d ms %n",
                    matrixSize, System.currentTimeMillis() - startTime);
        }

        MPI.Finalize();
    }
}