package sortstrategy;

import org.project.Product;
import org.sortstrategy.ShellSort;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


import java.util.*;
import java.io.*;


public class ShellSortTest {

    @Test
    void sortAllFields() {
        Product[] products = new Product[]{
                new Product.ProductBuilder().setName("brownie").setPrice(4.5).setQuantity(10).build(),
                new Product.ProductBuilder().setName("apple").setPrice(10.0).setQuantity(5).build(),
                new Product.ProductBuilder().setName("apple").setPrice(5.0).setQuantity(20).build(),
                new Product.ProductBuilder().setName("apple").setPrice(5.0).setQuantity(10).build()
        };

        ShellSort shellSort = new ShellSort();
        shellSort.sort(products);

        assertEquals("apple", products[0].getName());
        assertEquals(5.0, products[0].getPrice());
        assertEquals(10, products[0].getQuantity());


        assertEquals("apple", products[1].getName());
        assertEquals(5.0, products[1].getPrice());
        assertEquals(20, products[1].getQuantity());

        assertEquals("apple", products[2].getName());
        assertEquals(10.0, products[2].getPrice());
        assertEquals(5, products[2].getQuantity());

        assertEquals("brownie", products[3].getName());
    }

    @Test
    void EmptyArray() {
        Product[] products = new Product[]{};

        ShellSort shellSort = new ShellSort();
        shellSort.sort(products);

        assertEquals(0, products.length);
    }

    @Test
    void AlreadySortedArray() {
        Product[] products = new Product[]{
                new Product.ProductBuilder().setName("apple").setPrice(10.0).setQuantity(5).build(),
                new Product.ProductBuilder().setName("brownie").setPrice(4.5).setQuantity(10).build(),
        };

        ShellSort shellSort = new ShellSort();
        shellSort.sort(products);

        assertEquals("apple", products[0].getName());
        assertEquals("brownie", products[1].getName());
    }
}
