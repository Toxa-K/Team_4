package org.sortstrategy;

import org.project.Product;

public class SortContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Product[] products) {
        strategy.sort(products);
    }
}