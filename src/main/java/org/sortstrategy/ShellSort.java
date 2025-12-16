package org.sortstrategy;

import org.project.Product;

public class ShellSort implements SortStrategy {

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

        for (int gap = length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < length; i++) {
                Product temp = products[i];
                int j = i;

                while (j >= gap && compare(products[j - gap], temp) > 0) {
                    products[j] = products[j - gap];
                    j -= gap;
                }

                products[j] = temp;
            }
        }
    }
}