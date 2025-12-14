package org.inputstrategy;

import org.project.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualInputStrategy implements InputStrategy{
    private final Scanner scanner;

    public ManualInputStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<Product> load() {
        List<Product> productsList = new ArrayList<>();

        System.out.println("Сколько записей хотите добавить? : ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for(int index = 0; index < count; index++)
        {
            System.out.println("Запись: " + (index+1));
            System.out.print("Введите имя продукта: ");
            String name = scanner.nextLine();

            System.out.print("Введите цену: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Введите количество: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Product product = new Product.ProductBuilder()
                    .setName(name)
                    .setPrice(price)
                    .setQuantity(quantity)
                    .build();

            productsList.add(product);
        }
        return productsList;
    }
}
