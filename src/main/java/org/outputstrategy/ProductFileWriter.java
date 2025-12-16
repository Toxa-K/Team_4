package org.outputstrategy;

import org.project.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductFileWriter implements OutputStrategy {

    @Override
    public void writeToFile(List<Product> products, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (Product product : products) {
                writer.write(product.getName() + ";" + product.getPrice() + ";" + product.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}