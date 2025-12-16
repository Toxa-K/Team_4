package org.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ProductCollection {


    private final List<Product> products;

    public ProductCollection() {
        this.products = new ArrayList<>();
    }

    public ProductCollection(Collection<Product> products) {
        this.products = new ArrayList<>(products);
    }


    public void add(Product product) {
        products.add(product);
    }


    public void addAll(Collection<Product> products) {
        this.products.addAll(products);
    }


    public int size() {
        return products.size();
    }


    public boolean isEmpty() {
        return products.isEmpty();
    }


    public Stream<Product> stream() {
        return products.stream();
    }

    //Получить копию списка
    public List<Product> toList() {
        return new ArrayList<>(products);
    }


    public void clear() {
        products.clear();
    }

    public Product get(int index) {
        return products.get(index);
    }

    public void print() {
        if (products.isEmpty()) {
            System.out.println("Нет данных для отображения. Загрузите данные сначала.");
            return;
        }
        System.out.println("\nИмеющиеся данные");
        System.out.println("Количество записей: " + products.size());

        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }
}

