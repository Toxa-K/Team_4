package org.outputstrategy;

import org.project.Product;

import java.util.List;

public interface OutputStrategy {
    void writeToFile(List<Product> products, String fileName);
}