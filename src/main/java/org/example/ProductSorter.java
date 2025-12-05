package org.example;
import java.util.Comparator;
import java.util.List;

public class ProductSorter {
    // Сортировка по name.
    public static void sortByName(Product[]products){
        int n = products.length;
        boolean swapped;
        for (int i = 0; i < n-1; i++) {
            swapped = false;
            for(int j = 0; j < n - 1 - i; j++){
                if (products[j].getName().compareTo(products[j + 1].getName()) > 0){
                    Product temp = products[j];
                    products[j]= products[j + 1];
                    products[j + 1]= temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

}
