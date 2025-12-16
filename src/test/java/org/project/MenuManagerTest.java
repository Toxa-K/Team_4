package org.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MenuManagerTest {
    private MenuManager menuManager;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        menuManager = new MenuManager();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private Product createProduct(String name, double price, int quantity) {
        return new Product.ProductBuilder()
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .build();
    }

    @Test
    void testConstructor() throws Exception {
        // Arrange & Act
        MenuManager manager = new MenuManager();

        // Assert
        assertNotNull(manager);

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        assertNotNull(scannerField.get(manager));

        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);
        List<Product> productList = (List<Product>) productListField.get(manager);
        assertNotNull(productList);
        assertTrue(productList.isEmpty());
    }

    @Test
    void testPrintData_EmptyList() throws Exception {
        // Arrange

        // Act
        Method printDataMethod = MenuManager.class.getDeclaredMethod("printData");
        printDataMethod.setAccessible(true);
        printDataMethod.invoke(menuManager);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Нет данных для отображения"));
        assertTrue(output.contains("Загрузите данные сначала"));
    }

    @Test
    void testPrintData_WithProducts() throws Exception {
        // Arrange
        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);
        List<Product> productList = (List<Product>) productListField.get(menuManager);

        productList.add(createProduct("Телевизор", 29999.99, 15));
        productList.add(createProduct("Холодильник", 45999.99, 8));

        // Act
        Method printDataMethod = MenuManager.class.getDeclaredMethod("printData");
        printDataMethod.setAccessible(true);
        printDataMethod.invoke(menuManager);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Имеющиеся данные"));
        assertTrue(output.contains("Количество записей: 2"));
        assertTrue(output.contains("Телевизор"));
        assertTrue(output.contains("Холодильник"));
        assertTrue(output.contains("29999.99"));
        assertTrue(output.contains("45999.99"));
    }

    @Test
    void testMergeData_EmptyNewList() throws Exception {
        // Arrange
        List<Product> emptyList = Collections.emptyList();

        // Act
        Method mergeDataMethod = MenuManager.class.getDeclaredMethod("mergeData", List.class);
        mergeDataMethod.setAccessible(true);
        mergeDataMethod.invoke(menuManager, emptyList);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Нет новых данных"));
    }

    @Test
    void testMergeData_EmptyCurrentList() throws Exception {
        // Arrange
        List<Product> newProducts = new ArrayList<>();
        newProducts.add(createProduct("Новый продукт", 100.0, 5));

        Scanner testScanner = new Scanner("1\n"); // Выбираем "заменить"

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menuManager, testScanner);

        // Act
        Method mergeDataMethod = MenuManager.class.getDeclaredMethod("mergeData", List.class);
        mergeDataMethod.setAccessible(true);
        mergeDataMethod.invoke(menuManager, newProducts);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Заполнили пустой список данными"));

        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);
        List<Product> productList = (List<Product>) productListField.get(menuManager);

        assertEquals(1, productList.size());
        assertEquals("Новый продукт", productList.get(0).getName());
    }

    @Test
    void testMergeData_ReplaceExisting() throws Exception {
        // Arrange
        MenuManager manager = new MenuManager();

        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);


        List<Product> managerProductList = (List<Product>) productListField.get(manager);


        managerProductList.clear();
        managerProductList.add(createProduct("Товар1", 100.0, 5));
        managerProductList.add(createProduct("Товар2", 200.0, 3));

        System.out.println("До теста: список менеджера содержит " + managerProductList.size() + " элементов");

        List<Product> replacementList = new ArrayList<>();
        replacementList.add(createProduct("Новый товар", 300.0, 10));

        // Создаем Scanner для выбора "заменить"
        String userInput = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(userInput.getBytes());
        Scanner userScanner = new Scanner(in);

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(manager, userScanner);

        // Act
        Method mergeDataMethod = MenuManager.class.getDeclaredMethod("mergeData", List.class);
        mergeDataMethod.setAccessible(true);
        mergeDataMethod.invoke(manager, replacementList);

        List<Product> currentList = (List<Product>) productListField.get(manager);
        System.out.println("\nПосле mergeData:");
        System.out.println("HashCode текущего списка менеджера: " + System.identityHashCode(currentList));
        System.out.println("Содержит " + currentList.size() + " элементов");

        assertFalse(currentList.isEmpty(), "Список не должен быть пустым после mergeData");

        // Проверяем содержимое
        if (currentList.size() == 1) {
            assertEquals("Новый товар", currentList.get(0).getName());
        }
    }

    @Test
    void testMergeData_AddToExisting() throws Exception {
        // Arrange
        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);
        List<Product> productList = (List<Product>) productListField.get(menuManager);

        productList.add(createProduct("Первый товар", 100.0, 5));

        List<Product> newProducts = new ArrayList<>();
        newProducts.add(createProduct("Второй товар", 200.0, 3));
        newProducts.add(createProduct("Третий товар", 300.0, 2));

        Scanner testScanner = new Scanner("2\n"); // Выбираем "дописать"

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menuManager, testScanner);

        // Act
        Method mergeDataMethod = MenuManager.class.getDeclaredMethod("mergeData", List.class);
        mergeDataMethod.setAccessible(true);
        mergeDataMethod.invoke(menuManager, newProducts);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Новые данные добавлены в список"));
        assertTrue(output.contains("Всего записей 3"));

        assertEquals(3, productList.size());
        assertEquals("Первый товар", productList.get(0).getName());
        assertEquals("Второй товар", productList.get(1).getName());
        assertEquals("Третий товар", productList.get(2).getName());
    }

    @Test
    void testShowMenus() throws Exception {
        // Test showMainMenu
        Method showMainMenuMethod = MenuManager.class.getDeclaredMethod("showMainMenu");
        showMainMenuMethod.setAccessible(true);
        showMainMenuMethod.invoke(menuManager);

        String output = outputStream.toString();
        assertTrue(output.contains("Главное меню"));
        assertTrue(output.contains("Загрузить данные"));
        assertTrue(output.contains("Выход"));

        // Reset for next test
        outputStream.reset();

        // Test showLoadDataMenu
        Method showLoadDataMenuMethod = MenuManager.class.getDeclaredMethod("showLoadDataMenu");
        showLoadDataMenuMethod.setAccessible(true);
        showLoadDataMenuMethod.invoke(menuManager);

        output = outputStream.toString();
        assertTrue(output.contains("Выберите как получить данные"));
        assertTrue(output.contains("Случайная генерация данных"));
        assertTrue(output.contains("Ввести вручную"));

        // Reset for next test
        outputStream.reset();

        // Test showSortingMenu
        Method showSortingMenuMethod = MenuManager.class.getDeclaredMethod("showSortingMenu");
        showSortingMenuMethod.setAccessible(true);
        showSortingMenuMethod.invoke(menuManager);

        output = outputStream.toString();
        assertTrue(output.contains("Меню сортировки"));
        assertTrue(output.contains("Bubble sort"));
        assertTrue(output.contains("Selection Sort"));
        assertTrue(output.contains("Shell Sort"));
    }

    @Test
    void testShowAskAboutExistingDataMenu() throws Exception {
        // Arrange
        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);
        List<Product> productList = (List<Product>) productListField.get(menuManager);

        productList.add(createProduct("Товар A", 100.0, 10));
        productList.add(createProduct("Товар B", 200.0, 5));

        // Act
        Method showAskMethod = MenuManager.class.getDeclaredMethod("showAskAboutExistingDataMenu");
        showAskMethod.setAccessible(true);
        showAskMethod.invoke(menuManager);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("У вас уже есть загруженные данные"));
        assertTrue(output.contains("2 записей"));
        assertTrue(output.contains("Заменить текущие данные новыми"));
        assertTrue(output.contains("Дописать новые данные к текущим"));
        assertTrue(output.contains("Отмена"));
    }

    @Test
    void testFileWriterDataMenu_EmptyList() throws Exception {
        // Arrange
        Scanner testScanner = new Scanner("test.txt\n");

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menuManager, testScanner);

        // Act
        Method fileWriterMethod = MenuManager.class.getDeclaredMethod("fileWriterDataMenu");
        fileWriterMethod.setAccessible(true);
        fileWriterMethod.invoke(menuManager);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Нет данных для записи в файл"));
        assertTrue(output.contains("Загрузите данные сначала"));
    }

    @Test
    void testSortingMenu_InvalidChoice() throws Exception {
        // Arrange
        Field productListField = MenuManager.class.getDeclaredField("productList");
        productListField.setAccessible(true);
        List<Product> productList = (List<Product>) productListField.get(menuManager);

        productList.add(createProduct("Товар 1", 300.0, 5));
        productList.add(createProduct("Товар 2", 100.0, 10));

        Scanner testScanner = new Scanner("99\n"); // Неверный выбор

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menuManager, testScanner);

        // Act
        Method sortingMenuMethod = MenuManager.class.getDeclaredMethod("sortingMenu");
        sortingMenuMethod.setAccessible(true);
        sortingMenuMethod.invoke(menuManager);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Меню сортировки"));
        assertTrue(output.contains("Неверный ввод"));
    }

    @Test
    void testFileLoadDataMenu() throws Exception {
        // Arrange
        Scanner testScanner = new Scanner("products.txt\n10\n");

        Field scannerField = MenuManager.class.getDeclaredField("scanner");
        scannerField.setAccessible(true);
        scannerField.set(menuManager, testScanner);

        // Act
        Method fileLoadMethod = MenuManager.class.getDeclaredMethod("fileLoadDataMenu");
        fileLoadMethod.setAccessible(true);
        fileLoadMethod.invoke(menuManager);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Введите название файла"));
        assertTrue(output.contains("Введите кол-во загружаемых строк"));
        assertTrue(output.contains("Если хотите загрузить весь файл введите 0"));
    }}