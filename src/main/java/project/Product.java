package project;

public class Product {
    private final String name;
    private final double price;
    private final int quantity;

    private Product(ProductBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", price=" + price + ", quantity=" + quantity + '}';
    }

    public static class ProductBuilder {
        private String name;
        private double price;
        private int quantity;

        public ProductBuilder setName(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            this.name = name;
            return this;
        }

        public ProductBuilder setPrice(double price) {
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            this.price = price;
            return this;
        }

        public ProductBuilder setQuantity(int quantity) {
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
            this.quantity = quantity;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
