package org.counterstartegy;

import org.project.Product;

import java.util.List;

public class CounterContext {
    private CountStrategy strategy;

    public int execute(List<Product> products, Product target) {
        if (strategy == null) {
            throw new IllegalStateException("Стратегия не установлена");
        }

        return strategy.countOccurrences(products, target);
    }

    public void setStrategy(CountStrategy strategy) {
        this.strategy = strategy;
    }
}
