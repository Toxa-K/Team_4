package org.inputstrategy;

import org.project.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomInputStrategy implements InputStrategy {
    private final Scanner scanner;
    private final Random random = new Random();

    public RandomInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public List<Product> load() {
        System.out.println("Сколько данных сгенерировать? : ");
        int count = scanner.nextInt();
        scanner.nextLine();

        List<Product> productsList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Product product = new Product.ProductBuilder()
                    .setName("Product_" + i)
                    .setPrice(random.nextDouble(1000))
                    .setQuantity(random.nextInt(100))
                    .build();

            productsList.add(product);
        }

        return productsList;
    }
}
