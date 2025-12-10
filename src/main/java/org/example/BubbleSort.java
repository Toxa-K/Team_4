package org.example;

public class BubbleSort implements SortStrategy {

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
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (compare(arr[j], arr[j + 1]) > 0) {
                    Product tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
