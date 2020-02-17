package danyliuk.mykola;

public class Main {

    public static final int SIZE = 10000;
    public static final int THREADS_COUNT = 10;
    public static double[] vector = new double[SIZE];

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < SIZE; i++) {
            vector[i] = getRandomNumber();
        }

        double serialResult = 0.0;
        for (int i = 0; i < SIZE; i++){
            serialResult += Math.abs(vector[i]);
        }

        double parallelResult = getParallelResult();

        System.out.println("Serial result: " + serialResult);
        System.out.println("Parallel result: " + parallelResult);

    }

    private static double getParallelResult() throws InterruptedException {
        ThreadArrayCalc[] runners = new ThreadArrayCalc[THREADS_COUNT];
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < runners.length; i++) {
            int indexFrom = SIZE / THREADS_COUNT * i;
            int indexTo = i == THREADS_COUNT - 1 ? SIZE : SIZE / THREADS_COUNT * (i + 1);

            ThreadArrayCalc runner = new ThreadArrayCalc(vector, indexFrom, indexTo);
            runners[i] = runner;
            threads[i] = new Thread(runner);

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        double sum = 0.0;
        for (ThreadArrayCalc runner : runners) {
            double result = runner.getResult();
            sum += result;
        }

        return sum;
    }

    private static double getRandomNumber() {
        return Math.floor(Math.random() * 100);
    }
}
