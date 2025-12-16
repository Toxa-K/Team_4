package org.sortstrategy;

import org.project.Product;

public class SelectionSort implements SortStrategy {

    private int compare(Product a, Product b) {
        int result = a.getName().compareTo(b.getName());
        if (result != 0) return result;

        result = Double.compare(a.getPrice(), b.getPrice());
        if (result != 0) return result;

        return Integer.compare(a.getQuantity(), b.getQuantity());
    }

    @Override
    public void sort(Product[] products) {
        for (int i = 0; i < products.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < products.length; j++) {
                if (compare(products[j], products[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            Product temp = products[minIndex];
            products[minIndex] = products[i];
            products[i] = temp;
        }
    }
}