package danyliuk.mykola;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author Mykola Danyliuk
 */

public class Main {

    public static void main(String[] args) {
        int[] arr = new int [1000000];
        fillArray(arr);

        int desired = 1;
        System.out.printf("Number of elements equal %d is %d\n\n", desired, countOfElementsThatEquals(arr, desired));

        System.out.printf("The minimum value in the array is %d on position %d\n",
                getMinAndMaxInArray(arr)[0][1], getMinAndMaxInArray(arr)[0][0]);
        System.out.printf("The maximum value in the array is %d on position %d\n\n",
                getMinAndMaxInArray(arr)[1][1], getMinAndMaxInArray(arr)[1][0]);

        System.out.println("Checksum of the array is " + calculateChecksum(arr));
    }

    private static void fillArray(int[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (int) (Math.random() * 100);
    }

    private static int countOfElementsThatEquals(int[] arr, int number) {
        AtomicInteger atomicAccumulator = new AtomicInteger();
        IntStream.of(arr).parallel().forEach(x -> {
            if (x != number) return;

            int oldValue;
            int newValue;
            do {
                oldValue = atomicAccumulator.get();
                newValue = oldValue + 1;
            } while (!atomicAccumulator.compareAndSet(oldValue, newValue));
        });

        return atomicAccumulator.get();
    }

    private static int[][] getMinAndMaxInArray(int[] arr) {
        AtomicInteger atomicMin = new AtomicInteger(Integer.MIN_VALUE);
        AtomicInteger atomicMax = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger atomicMinIndex = new AtomicInteger();
        AtomicInteger atomicMaxIndex = new AtomicInteger();

        IntStream.range(0, arr.length).parallel().forEach(i -> {
            int oldMin;
            int newMin;
            int oldMax;
            int newMax;
            int oldMinIndex;
            int newMinIndex;
            int oldMaxIndex;
            int newMaxIndex;

            do {
                oldMin = atomicMin.get();
                oldMax = atomicMax.get();
                oldMinIndex = atomicMinIndex.get();
                oldMaxIndex = atomicMaxIndex.get();

                newMin = Math.min(arr[i], oldMin);
                newMax = Math.max(arr[i], oldMax);
                newMinIndex = arr[i] < oldMin ? i : oldMinIndex;
                newMaxIndex = arr[i] > oldMax ? i : oldMaxIndex;

            } while (!atomicMin.compareAndSet(oldMin, newMin) ||
                    !atomicMax.compareAndSet(oldMax, newMax) ||
                    !atomicMinIndex.compareAndSet(oldMinIndex, newMinIndex) ||
                    !atomicMaxIndex.compareAndSet(oldMaxIndex, newMaxIndex));
        });

        return new int[][]{
                { atomicMinIndex.get(), atomicMin.get() },
                { atomicMaxIndex.get(), atomicMax.get() }
        };
    }

    private static int calculateChecksum(int[] arr) {
        AtomicInteger atomicChecksum = new AtomicInteger();
        IntStream.of(arr).parallel().forEach(x -> {
            int oldValue;
            int newValue;
            do {
                oldValue = atomicChecksum.get();
                newValue = oldValue ^ x;
            } while (!atomicChecksum.compareAndSet(oldValue, newValue));
        });

        return atomicChecksum.get();
    }
}