package danyliuk.mykola;

import mpi.MPI;

public class MPIExample {

    private static final int SIZE = 5000000;

    public static void main(String [] args) throws InterruptedException {
        MPI.Init(args);
        double[] vector = new double[SIZE];
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == 0) {

            for (int i = 0; i < SIZE; i++) {
                vector[i] = getRandomNumber();
            }
        }
        double start_time = System.nanoTime();

        MPI.COMM_WORLD.Bcast(
                vector,
                0,
                SIZE,
                MPI.DOUBLE,
                0
        );

        int begin = (SIZE / size) * rank;
        int end = (rank == (size - 1)) ? SIZE : (SIZE / size) * (rank + 1);
        int norm = 0;

        for (int i = begin; i < end; i++) {
            norm += Math.abs(vector[i]);
        }

        int[] parallelResult = new int[1];
        parallelResult[0] = norm;
        MPI.COMM_WORLD.Reduce(
                parallelResult,
                0,
                parallelResult,
                0,
                1,
                MPI.INT,
                MPI.SUM,
                0
        );

        if (rank == 0) {
            double end_time = System.nanoTime();
            double parallel_time = end_time - start_time;
            System.out.format("\tParallel results: %d\n", parallelResult[0]);
            System.out.format("\tParallel time: %.3fs\n", parallel_time / 1e9);
        }
        MPI.Finalize();
    }

    private static double getRandomNumber() {
        return Math.floor(Math.random() * 100);
    }

}