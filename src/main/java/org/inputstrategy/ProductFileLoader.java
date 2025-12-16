package org.inputstrategy;

import org.project.Product;
import org.project.ProductCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductFileLoader implements InputStrategy {
    private final String fileName;
    private final int numOfLines;
    private Scanner scanner;

    public ProductFileLoader(String fileName, int numOfLines) {
        this(fileName, numOfLines, new Scanner(System.in));
    }

    public ProductFileLoader(String fileName, int numOfLines, Scanner scanner) {
        this.fileName = fileName;
        this.numOfLines = numOfLines == 0 ? Integer.MAX_VALUE : numOfLines;
        this.scanner = scanner;
    }

    public List<Product> loadFromFile(String fileName, int numOfLines) {
        List<Product> products = new ArrayList<>();
        int totalLines = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null && products.size() < numOfLines) {
                line = line.trim();
                totalLines++;
                if (line.isEmpty()) {
                    lineNumber++;
                    continue;
                }

                Product product = parseLine(line, lineNumber);
                if (product != null) {
                    products.add(product);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        if (totalLines < numOfLines && numOfLines != Integer.MAX_VALUE) {
            System.out.println("В файле меньше строк, чем ввёл пользователь: " + totalLines);
        }
        return products;
    }

    @Override
    public ProductCollection load() {
        return new ProductCollection(loadFromFile(fileName, numOfLines));
    }

    private Product parseLine(String line, int lineNumber) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            System.out.println("Строка " + lineNumber + " имеет неверный формат (ожидается 3 значения). Строка пропущена");
            return null;
        }

        boolean hasError = false;

        String name = parts[0].trim();
        if (name.isEmpty()) {
            System.out.println("Строка " + lineNumber + ": Пустое наименование.");
            hasError = true;
            name = null;
        }

        double price;
        try {
            price = Double.parseDouble(parts[1].trim());
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Строка " + lineNumber + ": Некорретная цена - " + parts[1].trim());
            hasError = true;
            price = 0.0;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(parts[2].trim());
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Строка " + lineNumber + ": Некорретное количество - " + parts[2].trim());
            hasError = true;
            quantity = 0;
        }

        if (hasError) {
            System.out.println("Если хотите оставить строку с нулевым значением, напишите \"да\", чтобы сохранить, иначе строка будет пропущена: ");
            String userInput = scanner.hasNextLine() ? scanner.nextLine().trim() : "нет";
            if (!userInput.equalsIgnoreCase("да")) {
                System.out.println("Строка " + lineNumber + " пропущена");
                return null;
            }
        }
        return new Product.ProductBuilder().setName(name).setPrice(price).setQuantity(quantity).build();
    }
}
