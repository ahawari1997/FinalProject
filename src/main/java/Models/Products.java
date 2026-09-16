package Models;

import java.math.BigDecimal;
import java.util.Objects;

public final class Products implements Comparable<Products> {
    private final String name;
    private final BigDecimal price;
    private final String description;

    public Products(String name, BigDecimal price, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be blank");
        }
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Product price must not be null or negative: " + price);
        }
        this.name = name;
        this.price = price;
        this.description = description == null ? "" : description;
    }

    public Products(String name, BigDecimal price) {
        this(name, price, "");

    }
    public String name() {
        return name;
    }

    public BigDecimal price() {
        return price;
    }

    public String description() {
        return description;
    }
    @Override
    public int compareTo(Products other) {
        return this.price.compareTo(other.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Products other)) return false;
        return name.equals(other.name) && price.compareTo(other.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + "}";
    }

}
