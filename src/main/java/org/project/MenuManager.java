package org.project;

import org.counterstartegy.CountStrategy;
import org.counterstartegy.CounterContext;
import org.counterstartegy.ThreadCounter;
import org.inputstrategy.ManualInputStrategy;
import org.inputstrategy.ProductFileLoader;
import org.inputstrategy.RandomInputStrategy;
import org.outputstrategy.ProductFileWriter;
import org.sortstrategy.BubbleSort;
import org.sortstrategy.SelectionSort;
import org.sortstrategy.ShellSort;
import org.sortstrategy.SortContext;

import java.util.Arrays;
import java.util.Scanner;

public class MenuManager {
    private final Scanner scanner;
    private final ProductCollection productCollection;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
        this.productCollection = new ProductCollection();
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            showMainMenu();
            int choice = getValidateChoice(1, 6);

            switch (choice) {
                case 1:
                    System.out.println("Загрузка данных");
                    loadDataMenu();
                    break;
                case 2:
                    System.out.println("Сортровка данных");
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
                    System.out.println("Подсчет вхождений элемента");
                    countOccurrencesMenu();
                    break;
                case 6:
                    isRunning = false;
                    System.out.println("Выход...");
                    break;
                default:
                    System.out.println("Неверный ввод. Выберите один из предложенных выриантов.");
            }
        }
    }

    private void countOccurrencesMenu() {
        if (productCollection.isEmpty()) {
            System.out.println("Нет данных для подсчета. Сначала загрузите данные.");
            return;
        }
        System.out.println("\nПодсчет количества вхождений");
        System.out.println("Всего продуктов: " + productCollection.size());

        if (!productCollection.isEmpty()) {
            System.out.println("\nПримеры продуктов в коллекции:");
            for (int i = 0; i < Math.min(3, productCollection.size()); i++) {
                System.out.println((i + 1) + ". " + productCollection.get(i));
            }
        }

        System.out.println("\nВведите данные для поиска");
        Product targetProduct = inputProductForSearch();

        if (targetProduct == null) {
            System.out.println("Не удалось создать продукт для поиска.");
            return;
        }
        CounterContext context = new CounterContext();
        CountStrategy strategy = new ThreadCounter();
        context.setStrategy(strategy);
        int result = context.execute(productCollection.toList(), targetProduct);
        System.out.println("Итог: найдено " + result + " совпадений ");
    }

    private Product inputProductForSearch() {
        String name;
        double price;
        int quantity;
        System.out.print("Введите имя продукта: ");
        while (true) {
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.print("Имя не может быть пустым! Введите имя продукта: ");
            } else {
                break;
            }
        }

        System.out.print("Введите цену: ");
        String priceToDouble;
        while (true) {
            priceToDouble = scanner.nextLine();

            try {
                price = Double.parseDouble(priceToDouble);
                if (price < 0) {
                    System.out.println("Цена не может быть отрицательной! Попробуйте снова: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Вам нужно ввести цену: ");
            }
        }


        System.out.print("Введите количество: ");
        String quantityToDouble;
        while (true) {
            quantityToDouble = scanner.nextLine();

            try {
                quantity = Integer.parseInt(quantityToDouble);
                if (quantity < 0) {
                    System.out.println("Количество не может быть отрицательным! Попробуйте снова: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Вам нужно ввести количество: ");
            }
        }
        return new Product.ProductBuilder()
                .setName(name)
                .setPrice(price)
                .setQuantity(quantity)
                .build();
    }

    private void sortingMenu() {
        if (productCollection.isEmpty()) {
            System.out.println("Нет данных для сортировки. Сначала загрузите данные.");
            return;
        }
        showSortingMenu();
        int choice = getValidateChoice(1, 4);

        SortContext context = new SortContext();
        Product[] array = productCollection.toList().toArray(new Product[0]);
        switch (choice) {
            case 1:
                System.out.println("Запуск Bubble sort");
                context.setStrategy(new BubbleSort());
                break;
            case 2:
                System.out.println("Запуск Selection Sort");
                context.setStrategy(new SelectionSort());
                break;
            case 3:
                System.out.println("Запуск Shell Sort");
                context.setStrategy(new ShellSort());
                break;
            case 4:
                return;
            default:
                System.out.println("Неверный ввод. Выберите один из предложенных выриантов.");
                break;
        }
        context.execute(array);

        productCollection.clear();
        productCollection.addAll(Arrays.asList(array));
    }

    private void loadDataMenu() {
        showLoadDataMenu();
        int choice = getValidateChoice(1, 4);

        switch (choice) {
            case 1:
                System.out.println("Cлучайная генерация");
                ProductCollection generatedProductList = new RandomInputStrategy(scanner).load();
                mergeData(generatedProductList);
                break;
            case 2:
                System.out.println("Ввести вручную");
                ProductCollection manualProductList = new ManualInputStrategy(scanner).load();
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

        if (productCollection.isEmpty()) {
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

        ProductCollection loaded = new ProductFileLoader(fileName, numOfLines).load();
        mergeData(loaded);
    }

    private void mergeData(ProductCollection generatedProductList) {
        if (generatedProductList == null || generatedProductList.isEmpty()) {
            System.out.println("Нет новых данных.");
            return;
        }

        if (productCollection.isEmpty()) {
            productCollection.addAll(generatedProductList.toList());
            System.out.println("Заполнили пустой список данными");
            return;
        }

        showAskAboutExistingDataMenu();
        int mergeChoice;

        try {
            mergeChoice = getValidateChoice(1, 3);
            switch (mergeChoice) {
                case 1:
                    productCollection.clear();
                    productCollection.addAll(generatedProductList.toList());
                    System.out.println("Текущие данные заменены");
                    break;
                case 2:
                    productCollection.addAll(generatedProductList.toList());
                    System.out.println("Новые данные добавлены в список. Всего записей " + productCollection.size());
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
        productCollection.print();
    }

    private void fileWriterDataMenu() {
        if (productCollection.isEmpty()) {
            System.out.println("Нет данных для записи в файл. Загрузите данные сначала.");
            return;
        }

        System.out.println("Введите название файла, в который хотите загрузить данные: ");
        String fileName = scanner.nextLine();

        ProductFileWriter writer = new ProductFileWriter();
        writer.writeToFile(productCollection.toList(), fileName);
        System.out.println("Данные успешно записаны в файл: " + fileName);
    }

    private void showSortingMenu() {
        System.out.println("\nМеню сортировки.");
        System.out.println("1. Bubble sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Shell Sort");
        System.out.println("4. Назад");
        System.out.print("Выберите пункт: ");
    }

    private void showMainMenu() {
        System.out.println("\nГлавное меню.");
        System.out.println("1. Загрузить данные");
        System.out.println("2. Отсортированть данные");
        System.out.println("3. Вывести данные в консоль");
        System.out.println("4. Записать данные в файл");
        System.out.println("5. Подсчет вхождений элемента");
        System.out.println("6. Выход");
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
        System.out.println("У вас уже есть загруженные данные (" + productCollection.size() + " записей).");
        System.out.println("1) Заменить текущие данные новыми");
        System.out.println("2) Дописать новые данные к текущим");
        System.out.println("3) Отмена");
        System.out.print("Выберите пункт: ");
    }

    private int getValidateChoice(int min, int max) {
        int choice = -1;
        boolean isValid = false;

        while (!isValid) {
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    isValid = true;
                } else {
                    System.out.printf("Ошибка: число должно быть в диапазоне от %d до %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число, а не текст.");
            }
        }
        return choice;
    }
}