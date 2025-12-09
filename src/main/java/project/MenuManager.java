package project;

//import Strategy.InputStrategy;
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
        //InputStrategy strategy = null;

        if (choice == 4) return;

        switch (choice) {
            case 1:
                System.out.println("В разработке"+"Cлучайная генерация");
                //strategy = new RandomInputStrategy(scanner);
                break;
            case 2:
                System.out.println("В разработке"+"Ввести вручную");
                //strategy = new ManualInputStrategy(scanner);
                break;
            case 3:
                System.out.println("Загрузить из файла");
                //strategy = new FileInputStrategy(scanner);
                break;
            default:
                System.out.println("Неверный ввод. Выберите один из предложенных выриантов.");
                break;
        }
        //productList = strategy.load();
        System.out.println("Данные загружены успешно");
    }
    private void fileLoadDataMenu(){
        System.out.println("Введите название файла из которого загрузить данные");
        //strategy = new FileInput();
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
