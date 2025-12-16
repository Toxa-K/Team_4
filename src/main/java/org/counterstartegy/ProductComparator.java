package org.counterstartegy;

import org.project.Product;

public class ProductComparator {
    public boolean compare(Product product, Product target) {
        if (product == null || target == null) {
            return false;
        }
        if (!product.getName().equals(target.getName())) {
            return false;
        }
        if (Math.abs(product.getPrice() - target.getPrice()) > 0.001) {
            return false;
        }
        return product.getQuantity() == target.getQuantity();
    }
}
