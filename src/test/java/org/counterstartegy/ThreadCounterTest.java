package org.counterstartegy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreadCounterTest {

    private ThreadCounter threadCounter;
    private Product testProduct;
    private Product otherProduct;

    @BeforeEach
    void setUp() {
        threadCounter = new ThreadCounter();
        testProduct = new Product.ProductBuilder()
                .setName("Test Product")
                .setPrice(100.0)
                .setQuantity(1)
                .build();

        otherProduct = new Product.ProductBuilder()
                .setName("Other Product")
                .setPrice(200.0)
                .setQuantity(1)
                .build();
    }

    @Test
    @DisplayName("Тест с пустым списком")
    void testCountWithEmptyList() {
        List<Product> emptyList = Collections.emptyList();
        int result = threadCounter.countOccurrences(emptyList, testProduct);
        assertEquals(0, result, "Для пустого списка должно возвращаться 0");
    }


    @Test
    @DisplayName("Тест с null продуктом")
    void testCountWithNullProduct() {
        List<Product> products = Arrays.asList(testProduct, testProduct);
        int result = threadCounter.countOccurrences(products, null);
        assertEquals(0, result, "Для null продукта должно возвращаться 0");
    }

    @Test
    @DisplayName("Тест с одним элементом (однопоточный режим)")
    void testSingleElementSingleThread() {
        List<Product> singleList = Collections.singletonList(testProduct);
        int result = threadCounter.countOccurrences(singleList, testProduct);
        assertEquals(1, result, "Должен найти один продукт в списке из одного элемента");
    }


    @Test
    @DisplayName("Тест с большим списком (многопоточный режим)")
    void testLargeListMultiThread() {
        List<Product> products = new ArrayList<>();

        // Создаем список из 1000 продуктов
        for (int i = 0; i < 500; i++) {
            products.add(testProduct);
            products.add(otherProduct);
        }

        int result = threadCounter.countOccurrences(products, testProduct);
        assertEquals(500, result, "Должен найти 500 тестовых продуктов");
    }

    @Test
    @DisplayName("Тест когда продукт не найден")
    void testProductNotFound() {
        List<Product> products = Arrays.asList(otherProduct, otherProduct, otherProduct);
        int result = threadCounter.countOccurrences(products, testProduct);
        assertEquals(0, result, "Должен вернуть 0, если продукт не найден");
    }

    @Test
    @DisplayName("Тест со всеми одинаковыми продуктами")
    void testAllSameProducts() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            products.add(testProduct);
        }

        int result = threadCounter.countOccurrences(products, testProduct);
        assertEquals(100, result, "Должен найти все 100 продуктов");
    }

    @Test
    @DisplayName("Тест граничных условий")
    void testBoundaryConditions() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            products.add(testProduct);
        }

        int result = threadCounter.countOccurrences(products, testProduct);
        assertEquals(2, result, "Должен корректно работать на границе переключения режимов");
    }

    @Test
    @DisplayName("Тест с продуктами разного типа")
    void testDifferentProductTypes() {
        Product product1 = new Product.ProductBuilder()
                .setName("Product1")
                .setPrice(100.0)
                .setQuantity(1)
                .build();

        Product product2 = new Product.ProductBuilder()
                .setName("Product2")
                .setPrice(200.0)
                .setQuantity(1)
                .build();

        Product product3 = new Product.ProductBuilder()
                .setName("Product3")
                .setPrice(300.0)
                .setQuantity(1)
                .build();

        List<Product> products = Arrays.asList(
                product1, product2, product1, product3, product2, product1
        );

        int result = threadCounter.countOccurrences(products, product1);
        assertEquals(3, result, "Должен найти 3 продукта первого типа(Product1)");
    }

    @Test
    @DisplayName("Тест с минимальными данными для многопоточности")
    void testMinDataForMultiThreading() {
        List<Product> products = new ArrayList<>();
        products.add(testProduct);
        products.add(otherProduct);

        int result = threadCounter.countOccurrences(products, testProduct);
        assertEquals(1, result, "Должен переключиться в многопоточный режим");
    }
}