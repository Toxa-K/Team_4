package project;

import org.project.Product;
import org.junit.jupiter.api.*;

import java.util.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void CreateProduct() {
        Product product = new Product.ProductBuilder()
                .setName("jam")
                .setPrice(10.24)
                .setQuantity(15)
                .build();

        assertEquals("jam", product.getName());
        assertEquals(10.24, product.getPrice());
        assertEquals(15, product.getQuantity());
    }

    @Test
    void PriceToRound() {
        Product product = new Product.ProductBuilder()
                .setName("jam")
                .setPrice(10.2379245152)
                .setQuantity(15)
                .build();

        assertEquals(10.24, product.getPrice());
    }

    @Test
    void EmptyNameException() {
        try {
            Product product = new Product.ProductBuilder()
                    .setName("")
                    .setPrice(10.0)
                    .setQuantity(15)
                    .build();

            fail("Ожидалось: IllegalArgumentException, но исключение не было выброшено");
        } catch (IllegalArgumentException e) {
            assertEquals("Наименование не может быть пустым", e.getMessage());
        }
    }

    @Test
    void NegativePriceException() {
        try {
            Product product = new Product.ProductBuilder()
                    .setName("jam")
                    .setPrice(-10.0)
                    .setQuantity(15)
                    .build();

            fail("Ожидалось: IllegalArgumentException, но исключение не было выброшено");
        } catch (IllegalArgumentException e) {
            assertEquals("Цена не может быть отрицательной", e.getMessage());
        }
    }

    @Test
    void NegativeQuantityException() {
        try {
            Product product = new Product.ProductBuilder()
                    .setName("jam")
                    .setPrice(10.0)
                    .setQuantity(-523)
                    .build();

            fail("Ожидалось: IllegalArgumentException, но исключение не было выброшено");
        } catch (IllegalArgumentException e) {
            assertEquals("Количество не может быть отрицательным", e.getMessage());
        }
    }
}
