package org.example;

public class SortContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Product[] products) {
        strategy.sort(products);
    }
}