package InputStrategy;

import org.junit.jupiter.api.*;
import org.outputstrategy.ProductFileWriter;
import org.project.Product;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductFileWriterTest {
    private final String testFile = "src/test/resources/test_pfw.txt";
    private ProductFileWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        writer = new ProductFileWriter();
        new File(testFile).delete();
    }

    @AfterEach
    void cleanUp() {
        new File(testFile).delete();
    }

    @Test
    void writeProductToEmptyFile() throws IOException {
        List<Product> products = List.of(
                new Product.ProductBuilder().setName("lemon").setPrice(4.5).setQuantity(40).build(),
                new Product.ProductBuilder().setName("cake").setPrice(10.0).setQuantity(5).build()
        );

        writer.writeToFile(products, testFile);

        List<String> lines = Files.readAllLines(Paths.get(testFile));

        assertEquals(2, lines.size());
        assertEquals("lemon;4.5;40", lines.get(0));
        assertEquals("cake;10.0;5", lines.get(1));
    }

    @Test
    void writeProductToNotEmptyFile() throws IOException {
        try(PrintWriter printer = new PrintWriter(testFile)) {
            printer.println("milk;3.0;10");
        }

        List<Product> products = List.of(
            new Product.ProductBuilder().setName("jam").setPrice(2.9).setQuantity(7).build()
        );

        writer.writeToFile(products, testFile);

        List<String> lines = Files.readAllLines(Paths.get(testFile));

        assertEquals(2, lines.size());
        assertEquals("milk;3.0;10", lines.get(0));
        assertEquals("jam;2.9;7", lines.get(1));
    }

    @Test
    void writeEmptyList() throws IOException {
        writer.writeToFile(new ArrayList<>(), testFile);

        File file = new File(testFile);
        assertTrue(file.exists());
        assertEquals(0, Files.size(Paths.get(testFile)));
    }

    @Test
    void shouldCreateFileIfNotExist() throws IOException {
        List<Product> products = List.of(
            new Product.ProductBuilder().setName("jam").setPrice(2.9).setQuantity(7).build()
        );

        writer.writeToFile(products, testFile);

        assertTrue(Files.exists(Paths.get(testFile)));

    }
}
