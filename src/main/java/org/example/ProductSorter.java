package org.example;
import java.util.Comparator;
import java.util.List;

public class ProductSorter {
    private static final Comparator<Product> PRODUCT_COMPARATOR =
            Comparator.comparing(Product::getName)
                    .thenComparingDouble(Product::getPrice)
                    .thenComparingInt(Product::getQuantity);

    public static void sort(List<Product> products) {
        products.sort(PRODUCT_COMPARATOR);
    }
}