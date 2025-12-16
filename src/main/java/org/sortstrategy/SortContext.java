package org.sortstrategy;

import org.project.Product;

public class SortContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        if (strategy == null) {
            throw new IllegalStateException("Стратегия не установлена");
        }
        this.strategy = strategy;
    }

    public void execute(Product[] products) {
        strategy.sort(products);
    }
}