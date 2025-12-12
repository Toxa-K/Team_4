package Strategy;

import org.junit.jupiter.api.*;
import project.Product;

import java.util.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductFileLoaderTest {
    private final String testFile = "src/test/resources/test_pfl.txt";
    private final Scanner scanner = new Scanner(System.in);

    @BeforeEach
    void setUp() throws IOException {
        try (PrintWriter printer = new PrintWriter(testFile)) {
            printer.println("lemon;4.5;40");
            printer.println("cake;10.0;5");
            printer.println("jam;2.9;7");
            printer.println("milk;wrq;3");
            printer.println(";2.3;5");
            printer.println("melon;4.3;fs");
            printer.println("n;y;a");
        }
    }

    @AfterEach
    void cleanUp() {
        new File(testFile).delete();
    }

    @Test
    void testLoadCorrectProducts () throws IOException {
        Scanner fakeInput = new Scanner("");
        ProductFileLoader loader = new ProductFileLoader(testFile,2,fakeInput);

        List<Product> products = loader.loadFromFile(testFile, 2);

        assertEquals(2, products.size());
        Product lemon = products.get(0);
        assertEquals("lemon",lemon.getName());
        assertEquals(4.5,lemon.getPrice());
        assertEquals(40,lemon.getQuantity());
    }

    @Test
    void testSaveIncorrectLine () throws IOException {
        Scanner fakeInput = new Scanner("да\n");
        ProductFileLoader loader = new ProductFileLoader(testFile,4,fakeInput);

        List<Product> products = loader.loadFromFile(testFile, 4);

        assertEquals(4,products.size());
        Product milk = products.get(3);
        assertEquals("milk",milk.getName());
        assertEquals(0.0,milk.getPrice());
        assertEquals(3,milk.getQuantity());
    }

    @Test
    void testSkipIncorrectLine() {
        Scanner fakeInput = new Scanner("нет\nнет\nнет\nнет\n");
        ProductFileLoader loader = new ProductFileLoader(testFile,7,fakeInput);

        List<Product> products = loader.loadFromFile(testFile, 7);

        assertEquals(3, products.size());
    }

    @Test
    void testEmptyFile () throws IOException {
        String emptyFile = "src/test/resources/empty.txt";
        new PrintWriter(emptyFile).close();

        Scanner fakeInput = new Scanner("");
        ProductFileLoader loader = new ProductFileLoader(emptyFile,10,fakeInput);

        List<Product> products = loader.loadFromFile(emptyFile, 10);

        assertTrue(products.isEmpty(), "Список должен быть пустым");

        new File(emptyFile).delete();
    }

    @Test
    void testFileNotFound () throws IOException {
        Scanner fakeInput = new Scanner("");
        ProductFileLoader loader = new ProductFileLoader("nyahaha.txt",10,fakeInput);

        List<Product> products = loader.loadFromFile("nyahaha.txt", 10);

        assertTrue(products.isEmpty());
    }
}
