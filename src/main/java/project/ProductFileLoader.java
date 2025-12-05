package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFileLoader {

    public List<Product> loadFromFile(String fileName, int numOfLines) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null && products.size() < numOfLines) {
                line = line.trim();
                if (line.isEmpty()) {
                    lineNumber++;
                    continue;
                }

                try {
                    Product product = parseLine(line);
                    products.add(product);
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка парсинга строки под номером " + lineNumber + ": " + e.getMessage());
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        if (products.size() < numOfLines && numOfLines != Integer.MAX_VALUE) {
            System.out.println("В файле меньше строк, чем ожидал пользователь: " + products.size());
        }
        return products;
    }

    public List<Product> loadFromFile(String fileName) {
        return loadFromFile(fileName, Integer.MAX_VALUE);
    }

    private Product parseLine(String line) {
        String[] parts = line.split(";");

        if (parts.length != 3)
            throw new IllegalArgumentException("Неверный формат ввода в строке, ожидается 3 значения, получено: " + parts.length);

        String name = parts[0].trim();
        if (name.isEmpty()) {
            //throw new IllegalArgumentException("Наименование товара не может быть пустым");
            name = null;
        }

        double price;
        try {
            price = Double.parseDouble(parts[1].trim());
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            //throw new IllegalArgumentException("Некорректная цена: " + parts[1].trim());
            price = 0.0;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(parts[2].trim());
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            //throw new IllegalArgumentException("Некорректное количество: " + parts[2].trim());
            quantity = 0;
        }

        return new Product.ProductBuilder().setName(name).setPrice(price).setQuantity(quantity).build();
    }
}
