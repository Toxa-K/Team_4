package org.inputstrategy;

import org.project.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualInputStrategy implements InputStrategy {
    private final Scanner scanner;

    public ManualInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Product> load() {
        List<Product> products = new ArrayList<>();

        System.out.println("Сколько записей хотите добавить? : ");
        String input;
        int count = 0;
        while (true) {
            input = scanner.nextLine();

            try {
                count = Integer.parseInt(input);
                if (count < 0) {
                    System.out.println("Количество записей не может быть отрицательным! Попробуйте снова: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Вам нужно ввести количество записей, которые хотите добавить: ");
            }
        }

        for (int index = 0; index < count; index++) {
            System.out.println("Запись: " + (index + 1));
            System.out.print("Введите имя продукта: ");
            String name;
            while (true) {
                name = scanner.nextLine();
                if (name.isEmpty() || name.isBlank() || name.equalsIgnoreCase("null")) {
                    System.out.println("Наименование не может быть пустым! Введите имя: ");
                    continue;
                }
                break;
            }

            System.out.print("Введите цену: ");
            String priceToDouble;
            double price = 0.0;
            while (true) {
                priceToDouble = scanner.nextLine();

                try {
                    price = Double.parseDouble(priceToDouble);
                    if (price < 0) {
                        System.out.println("Цена не может быть отрицательной! Попробуйте снова: ");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод! Вам нужно ввести цену: ");
                }
            }


            System.out.print("Введите количество: ");
            String quantityToDouble;
            int quantity = 0;
            while (true) {
                quantityToDouble = scanner.nextLine();

                try {
                    quantity = Integer.parseInt(quantityToDouble);
                    if (quantity < 0) {
                        System.out.println("Количество не может быть отрицательным! Попробуйте снова: ");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный ввод! Вам нужно ввести количество: ");
                }
            }

            Product product = new Product.ProductBuilder()
                    .setName(name)
                    .setPrice(price)
                    .setQuantity(quantity)
                    .build();

            products.add(product);
        }

        return products;
    }
}
