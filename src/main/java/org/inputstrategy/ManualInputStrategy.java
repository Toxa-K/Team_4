package org.inputstrategy;

import org.project.Product;
import org.project.ProductCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ManualInputStrategy implements InputStrategy {
    private final Scanner scanner;

    public ManualInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public ProductCollection load() {
        ProductCollection collectionProduct = new ProductCollection();

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


        IntStream.range(0, count)
                .mapToObj(index -> {
                    System.out.println("Запись: " + (index + 1));
                    return new Product.ProductBuilder()
                            .setName(readName())
                            .setPrice(readPrice())
                            .setQuantity(readQuantity())
                            .build();
                })
                .forEach(collectionProduct::add);

        return collectionProduct;
    }

    private String readName(){
        System.out.print("Введите имя продукта: ");
        String name;
        while (true) {
            name = scanner.nextLine();
            if (name.isEmpty() || name.isBlank() || name.equalsIgnoreCase("null")) {
                System.out.println("Наименование не может быть пустым! Введите имя: ");
                continue;
            }
            return name;
        }
    }
    private double readPrice(){
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
                return price;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Вам нужно ввести цену: ");
            }
        }
    }
    private int readQuantity(){
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
                return quantity;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Вам нужно ввести количество: ");
            }
        }
    }
}
