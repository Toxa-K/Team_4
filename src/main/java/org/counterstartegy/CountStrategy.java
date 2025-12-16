package org.counterstartegy;

import org.project.Product;

import java.util.List;

public interface CountStrategy {
    int countOccurrences(List<Product> products, Product target);
}
