package org.inputstrategy;

import org.project.Product;
import org.project.ProductCollection;
import org.project.RandomProductName;

import java.util.*;
import java.util.stream.Stream;

public class RandomInputStrategy implements InputStrategy {
    private final Scanner scanner;
    private final Random random = new Random();

    public RandomInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public ProductCollection load() {
        System.out.println("Сколько данных сгенерировать? : ");
        int count = readPositiveInt();
        /*String input;
        int count = 0;
        while (true) {
            input = scanner.nextLine();

            try {
                count = Integer.parseInt(input);
                if (count < 0) {
                    System.out.println("Число не может быть отрицательным");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Попробуйте снова: ");
            }
        }*/

        ProductCollection collectionProduct = new ProductCollection();

        /*for (int i = 0; i < count; i++) {
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
                    collectionProduct.add(product);
        }
        return collectionProduct;*/
        Stream.generate(this::generateRandomProduct)
                .limit(count)
                .forEach(collectionProduct::add);

        return collectionProduct;
    }

    private int readPositiveInt() {
        String input;
        int count = 0;
        while (true) {
            input = scanner.nextLine();
            try {
                count = Integer.parseInt(input);
                if (count < 0) {
                    System.out.println("Число не может быть отрицательным");
                    continue;
                }
                return count;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Попробуйте снова: ");
            }

        }
    }

    private Product generateRandomProduct() {
        String nameProduct = RandomProductName.getRandomProduct();

        if (random.nextDouble() < 0.3) {
            nameProduct = nameProduct + " " + (random.nextInt(900) + 100);
        }
        double price = 10.0 + random.nextDouble() * 490.0;
        price = Math.round(price * 100.0) / 100.0;

        int quantity = random.nextInt(201);

        return new Product.ProductBuilder()
                .setName(nameProduct)
                .setPrice(price)
                .setQuantity(quantity)
                .build();
    }

}
