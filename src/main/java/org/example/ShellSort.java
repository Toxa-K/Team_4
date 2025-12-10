package org.example;

public class ShellSort implements SortStrategy {

    private int compare(Product a, Product b) {
        int r = a.getName().compareTo(b.getName());
        if (r != 0) return r;

        r = Double.compare(a.getPrice(), b.getPrice());
        if (r != 0) return r;

        return Integer.compare(a.getQuantity(), b.getQuantity());
    }

    @Override
    public void sort(Product[] arr) {
        int n = arr.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Product temp = arr[i];
                int j = i;

                while (j >= gap && compare(arr[j - gap], temp) > 0) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }

                arr[j] = temp;
            }
        }
    }
}