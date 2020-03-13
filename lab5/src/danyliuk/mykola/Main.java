package danyliuk.mykola;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author Mykola Danyliuk
 */
public class Main {
    private static final Random random = new Random(System.currentTimeMillis());
    private static final int LISTS_SIZE = 17;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();

        for (int i = 0; i < LISTS_SIZE; ++i) {
            listA.add(random.nextInt(10));
            listB.add(random.nextInt(20));
        }

        System.out.println("List A:" + listA);
        System.out.println("List B:" + listB);

        CompletableFuture<List<Integer>> completableFutureListA = CompletableFuture
                .supplyAsync(() -> listA.stream().parallel().mapToInt(i -> i).max().orElseThrow(NoSuchElementException::new))
                .thenApplyAsync((max) -> listA.stream().parallel().filter((i) -> i > max * 0.6).collect(Collectors.toList()))
                .thenApplyAsync((filteredListA) -> filteredListA.stream().parallel().sorted().collect(Collectors.toList()));

        CompletableFuture<List<Integer>> completableFutureListB = CompletableFuture
                .supplyAsync(() -> listB.stream().parallel().mapToInt(i -> i))
                .thenApplyAsync((o) -> listB.stream().parallel().filter((i) -> i % 2 == 0).collect(Collectors.toList()))
                .thenApplyAsync((filteredListB) -> filteredListB.stream().parallel().sorted().collect(Collectors.toList()));

        CompletableFuture<List<Integer>> result = completableFutureListA.thenCombine(completableFutureListB, (sortedListA, sortedListB) -> {
            List<Integer> mergedList = new ArrayList<>();
            int sortedListASize = sortedListA.size();
            int sortedListBSize = sortedListB.size();

            System.out.println("Sorted List A:" + sortedListA);
            System.out.println("Sorted List B:" + sortedListB);

            Integer current = null;

            while(!sortedListA.isEmpty() && !sortedListB.isEmpty()) {
                Integer sortedListAFirst = sortedListA.get(0);
                Integer sortedListBFirst = sortedListB.get(0);
                if(current != null){
                    if(sortedListAFirst.equals(current)){
                        mergedList.add(sortedListA.remove(0));
                    } else if(sortedListBFirst.equals(current)){
                        mergedList.add(sortedListB.remove(0));
                    } else {
                        current = null;
                    }
                } else {
                    if (sortedListAFirst.equals(sortedListBFirst)){
                        current = sortedListA.get(0);
                    } else if (sortedListAFirst < sortedListBFirst){
                        sortedListA.remove(0);
                    } else {
                        sortedListB.remove(0);
                    }
                }
            }

            return mergedList;
        });

        System.out.println("Result: " + result.get());
    }
}
