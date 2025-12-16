package org.inputstrategy;

import org.project.Product;
import org.project.RandomProductName;

import java.util.*;

public class RandomInputStrategy implements InputStrategy {
    private final Scanner scanner;
    private final Random random = new Random();

    public RandomInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Product> load() {
        System.out.println("Сколько данных сгенерировать? : ");
        String input;
        int count = 0;
        while (true) {
            input = scanner.nextLine();

            try {
                count = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Попробуйте снова: ");
            }
        }

        List<Product> products = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String nameProduct = RandomProductName.getRandomProduct();

            if (random.nextDouble() < 0.3) {
                nameProduct = nameProduct + " " + (random.nextInt(900) + 100);
            }
            double price = 10.0 + random.nextDouble() * 490.0;
            price = Math.round(price * 100.0) / 100.0;

            int quantity = random.nextInt(201);

            Product product = new Product.ProductBuilder()
                    .setName(nameProduct)
                    .setPrice(price)
                    .setQuantity(quantity)
                    .build();

            products.add(product);
        }

        return products;
    }
}
