package inputstrategy;

import org.inputstrategy.RandomInputStrategy;
import org.junit.jupiter.api.*;
import org.project.Product;

import java.util.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomInputStrategyTest {

    @Test
    void shouldGenerateExactAmountProducts() {
        Scanner fakeInput = new Scanner("10\n");
        RandomInputStrategy strategy = new RandomInputStrategy(fakeInput);

        List<Product> products = strategy.load();

        assertEquals(10, products.size());
    }

    @Test
    void shouldGenerateValidProducts() {
        Scanner fakeInput = new Scanner("50\n");
        RandomInputStrategy strategy = new RandomInputStrategy(fakeInput);

        List<Product> products = strategy.load();

        assertEquals(50, products.size());

        for (Product product : products) {
            assertNotNull(product);

            assertFalse(product.getName().isBlank());

            assertTrue(product.getPrice() > 0);
            assertTrue(product.getPrice() <= 500);

            assertTrue(product.getQuantity() >= 0);
            assertTrue(product.getQuantity() <= 200);

            double rounded = Math.round(product.getPrice() * 100.0) / 100.0;
            assertEquals(rounded, product.getPrice());
        }
    }

    @Test
    void zeroInput() {
        Scanner fakeInput = new Scanner("0\n");
        RandomInputStrategy strategy = new RandomInputStrategy(fakeInput);

        List<Product> products = strategy.load();

        assertNotNull(products);
        assertEquals(0, products.size());
    }

    @Test
    void incorrectInput() throws IOException {
        Scanner fakeInput = new Scanner("dasf\n10\n");
        RandomInputStrategy strategy = new RandomInputStrategy(fakeInput);

        List<Product> products = strategy.load();

        assertEquals(10, products.size());
    }
}
