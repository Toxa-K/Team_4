package project;


import Strategy.ManualInputStrategy;
import Strategy.ProductFileLoader;
import Strategy.RandomInputStrategy;
import java.util.ArrayList;
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
                    System.out.println("В разработке"+"Сортровка данных");
                    sortingMenu();
                    break;
                case 3:
                    System.out.println("Вывести данные в консоль");
                    printData();
                    break;
                case 4:
                    System.out.println("В разработке"+"Записать данные в файл");
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
        //SortingStrategy strategy;
        switch (choice) {
            case 1:
                System.out.println("В разработке");
                return;
            default:
                System.out.println("Неверный ввод. Выберите один из предложенных выриантов.");
                break;
        }
    }

    private void loadDataMenu(){
        showLoadDataMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Cлучайная генерация");
                productList = new RandomInputStrategy(scanner).load();
                break;
            case 2:
                System.out.println("Ввести вручную");
                productList = new ManualInputStrategy(scanner).load();
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
            System.out.println("Данные загружены успешно");//Добавить проверку
        }
    }

    private void fileLoadDataMenu(){

        System.out.println("Введите название файла из которого загрузить данные");
        String fileName = scanner.nextLine();
        System.out.println("Введите кол-во загружаемых строк \nЕсли хотите загрузить весь файл введите 0");
        int numOfLines = scanner.nextInt();
        productList = new ProductFileLoader(fileName, numOfLines).load();
    }

    private void printData() {
        if ( productList == null || productList.isEmpty()) {
            System.out.println("Нет данных для отображения. Загрузите данные сначала.");
            return;
        }
        System.out.println("\nИмеющиеся данные");
        System.out.println("Количество записей: " + productList.size());
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i));
        }
    }

    private void showSortingMenu(){
        System.out.println("\nМеню сортировки.");
        System.out.println("1. Выход");
        System.out.print("Выберите пункт: ");
    }
    private void showMainMenu(){
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
}