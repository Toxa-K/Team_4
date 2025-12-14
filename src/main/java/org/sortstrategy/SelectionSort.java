package org.sortstrategy;

import org.project.Product;

public class SelectionSort implements SortStrategy {

    private int compare(Product a, Product b) {
        int r = a.getName().compareTo(b.getName());
        if (r != 0) return r;

        r = Double.compare(a.getPrice(), b.getPrice());
        if (r != 0) return r;

        return Integer.compare(a.getQuantity(), b.getQuantity());
    }

    @Override
    public void sort(Product[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            Product temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}