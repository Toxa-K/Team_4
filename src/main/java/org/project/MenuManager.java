package org.project;

import org.inputstrategy.InputStrategy;
import org.inputstrategy.ManualInputStrategy;
import org.inputstrategy.ProductFileLoader;
import org.inputstrategy.RandomInputStrategy;
import org.outputstrategy.ProductFileWriter;
import org.sortstrategy.BubbleSort;
import org.sortstrategy.SelectionSort;
import org.sortstrategy.ShellSort;
import org.sortstrategy.SortContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner;
    private List<Product> productList;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
        this.productList = new ArrayList<>();
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Загрузка данных");
                    loadDataMenu();
                    break;
                case 2:
                    System.out.println("В разработке" + "Сортровка данных");
                    sortingMenu();
                    break;
                case 3:
                    System.out.println("Вывести данные в консоль");
                    printData();
                    break;
                case 4:
                    System.out.println("Записать данные в файл");
                    fileWriterDataMenu();
                    break;
                case 5:
                    isRunning = false;
                    System.out.println("Выход...");
                    break;
                default:
                    System.out.println("Неверный ввод. Выберите один из предложенных выриантов.");
            }
        }
    }

    private void sortingMenu() {
        showSortingMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();
        SortContext context = new SortContext();
        Product[] array = productList.toArray(new Product[0]);
        switch (choice) {
            case 1:
                System.out.println("Bubble sort");
                context.setStrategy(new BubbleSort());
                context.execute(array);
                productList = new ArrayList<>(Arrays.asList(array));
                break;
            case 2:
                System.out.println("Selection Sort");
                context.setStrategy(new SelectionSort());
                context.execute(array);
                productList = new ArrayList<>(Arrays.asList(array));
                break;
            case 3:
                System.out.println("Shell Sort");
                context.setStrategy(new ShellSort());
                context.execute(array);
                productList = new ArrayList<>(Arrays.asList(array));
                break;
            default:
                System.out.println("Неверный ввод. Выберите один из предложенных выриантов.");
                break;
        }
    }

    private void loadDataMenu() {
        showLoadDataMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Cлучайная генерация");
                List<Product> generatedProductList = new RandomInputStrategy(scanner).load();
                mergeData(generatedProductList);
                break;
            case 2:
                System.out.println("Ввести вручную");
                List<Product> manualProductList = new ManualInputStrategy(scanner).load();
                mergeData(manualProductList);
                break;
            case 3:
                System.out.println("Загрузить из файла");
                fileLoadDataMenu();
                break;
            case 4:
                return;
            default:
                System.out.println("Неверный ввод. Выберите один из предложенных вариантов.");
                break;
        }

        if (productList.isEmpty()) {
            System.out.println("Не удалось загрузить данные из файла");
        } else {
            System.out.println("Данные загружены успешно");
        }
    }

    private void fileLoadDataMenu() {
        System.out.println("Введите название файла, из которого загрузить данные: ");
        String fileName = scanner.nextLine();

        System.out.println("Введите кол-во загружаемых строк: \nЕсли хотите загрузить весь файл введите 0");
        int numOfLines = scanner.nextInt();

        productList = new ProductFileLoader(fileName, numOfLines).load();
    }

    private void mergeData(List<Product> generatedProductList) {
        if (generatedProductList == null || generatedProductList.isEmpty()) {
            System.out.println("Нет новых данных.");
            return;
        }

        if (productList == null || productList.isEmpty()) {
            productList = generatedProductList;
            System.out.println("Заполнили пустой список данными");
            return;
        }
        showAskAboutExistingDataMenu();
        int mergeChoice;

        try {
            mergeChoice = scanner.nextInt();
            scanner.nextLine();
            switch (mergeChoice) {
                case 1:
                    productList = generatedProductList;
                    System.out.println("Текущие данные заменены");
                    break;
                case 2:
                    productList.addAll(generatedProductList);
                    System.out.println("Новые данные добавлены в список. Всего записей " + productList.size());
                    break;
                default:
                    System.out.println("Отмена загрузки");
            }
        } catch (Exception invalidInput) {
            scanner.nextLine();
            System.out.println("Некорректный ввод");

        }
    }

    private void printData() {
        if (productList == null || productList.isEmpty()) {
            System.out.println("Нет данных для отображения. Загрузите данные сначала.");
            return;
        }
        System.out.println("\nИмеющиеся данные");
        System.out.println("Количество записей: " + productList.size());

        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i));
        }
    }

    private void fileWriterDataMenu() {
        if (productList == null || productList.isEmpty()) {
            System.out.println("Нет данных для записи в файл. Загрузите данные сначала.");
            return;
        }

        System.out.println("Введите название файла, в который хотите загрузить данные: ");
        String fileName = scanner.nextLine();

        ProductFileWriter writer = new ProductFileWriter();
        writer.writeToFile(productList, fileName);
        System.out.println("Данные успешно записаны в файл: " + fileName);
    }

    private void showSortingMenu() {
        System.out.println("\nМеню сортировки.");
        System.out.println("1. Bubble sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Shell Sort");
        System.out.println("4. Базовая сортировка");
        System.out.println("5. Выход");
        System.out.print("Выберите пункт: ");
    }

    private void showMainMenu() {
        System.out.println("\nГлавное меню.");
        System.out.println("1. Загрузить данные");
        System.out.println("2. Отсортированть данные");
        System.out.println("3. Вывести данные в консоль");
        System.out.println("4. Записать данные в файл");
        System.out.println("5. Выход");
        System.out.print("Выберите пункт: ");
    }

    private void showLoadDataMenu() {
        System.out.println("\nВыберите как получить данные.");
        System.out.println("1. Случайная генерация данных");
        System.out.println("2. Ввести вручную");
        System.out.println("3. Загрузить из файла");
        System.out.println("4. Назад");
        System.out.print("Выберите пункт: ");
    }

    private void showAskAboutExistingDataMenu() {
        System.out.println("У вас уже есть загруженные данные (" + productList.size() + " записей).");
        System.out.println("1) Заменить текущие данные новыми");
        System.out.println("2) Дописать новые данные к текущим");
        System.out.println("3) Отмена");
        System.out.print("Выберите пункт: ");
    }
}