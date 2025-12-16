package org.sortstrategy;

import org.project.Product;

public class BubbleSort implements SortStrategy {

    private int compare(Product a, Product b) {
        int result = a.getName().compareTo(b.getName());
        if (result != 0) return result;

        result = Double.compare(a.getPrice(), b.getPrice());
        if (result != 0) return result;

        return Integer.compare(a.getQuantity(), b.getQuantity());
    }

    @Override
    public void sort(Product[] products) {
        int length = products.length;
        boolean swapped;

        for (int i = 0; i < length - 1; i++) {
            swapped = false;

            for (int j = 0; j < length - 1 - i; j++) {
                if (compare(products[j], products[j + 1]) > 0) {
                    Product temp = products[j];
                    products[j] = products[j + 1];
                    products[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }
}
