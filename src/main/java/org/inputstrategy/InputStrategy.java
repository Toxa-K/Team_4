package org.inputstrategy;

import org.project.Product;

import java.util.List;

public interface InputStrategy {
    List<Product> load();
}
