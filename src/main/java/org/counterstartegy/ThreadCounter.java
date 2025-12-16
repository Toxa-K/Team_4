package org.counterstartegy;

import org.project.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadCounter implements CountStrategy {

    private final ProductComparator comparator;
    private final int threadCount;
    private final int minSizeForMultiThreading;

    public ThreadCounter() {
        this.comparator = new ProductComparator();
        this.threadCount = Runtime.getRuntime().availableProcessors();
        this.minSizeForMultiThreading = 1;
    }

    private int calculateThreadCount(int size) {
        return Math.min(threadCount, size);
    }

    @Override
    public int countOccurrences(List<Product> products, Product target) {
        if (products == null || products.isEmpty() || target == null) {
            return 0;
        }
        if (products.size() <= minSizeForMultiThreading) {
            System.out.println("Используется однопоточный режим (мало данных)");
            return singleThreadCount(products, target);
        }
        System.out.printf("Используется многопоточный режим (%d потоков)%n", calculateThreadCount(products.size()));
        return multiThreadCount(products, target);
    }

    private int singleThreadCount(List<Product> products, Product target) {
        int count = 0;
        for (Product product : products) {
            if (comparator.compare(product, target)) {
                count++;
            }
        }
        return count;
    }


    private int multiThreadCount(List<Product> products, Product target) {
        int optimalThreads = calculateThreadCount(products.size());
        ExecutorService executor = Executors.newFixedThreadPool(optimalThreads);
        List<Future<Integer>> futures = new ArrayList<>();
        int chunkSize = products.size() / optimalThreads;
        int remaining = products.size() % optimalThreads;
        int startIndex = 0;
        for (int i = 0; i < optimalThreads; i++) {
            int endIndex = startIndex + chunkSize + (i < remaining ? 1 : 0);
            if (endIndex > products.size()) {
                endIndex = products.size();
            }
            if (startIndex >= endIndex) {
                break;
            }
            List<Product> chunk = products.subList(startIndex, endIndex);
            CountingTask task = new CountingTask(chunk, target, comparator);
            futures.add(executor.submit(task));
            startIndex = endIndex;
        }
        int totalCount = 0;
        for (Future<Integer> future : futures) {
            try {
                totalCount += future.get();
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении потока: " + e.getMessage());
            }
        }
        executor.shutdown();
        return totalCount;
    }


    private static class CountingTask implements Callable<Integer> {
        private final List<Product> products;
        private final Product target;
        private final ProductComparator comparator;

        public CountingTask(List<Product> products, Product target, ProductComparator comparator) {
            this.products = new ArrayList<>(products);
            this.target = target;
            this.comparator = comparator;
        }


        @Override
        public Integer call() {
            int count = 0;
            for (Product product : products) {
                if (comparator.compare(product, target)) {
                    count++;
                }
            }
            return count;
        }
    }
}
