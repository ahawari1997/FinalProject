package Models;

import Utils.Priceutills;

import java.math.BigDecimal;
import java.util.List;

public class CartSummery {
    private final List<Products> productss;
    private final BigDecimal taxRate;

    public CartSummery(List<Products> products, BigDecimal taxRate) {
        if (products == null) {
            throw new IllegalArgumentException("products must not be null");
        }
        if (taxRate == null || taxRate.signum() < 0) {
            throw new IllegalArgumentException("taxRate must not be null or negative");
        }
        this.productss = List.copyOf(products);
        this.taxRate = taxRate;
    }

    public BigDecimal itemTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Products products : productss) {
            sum = sum.add(products.price());
        }
        return Priceutills.round(sum);
    }

    public BigDecimal tax() {
        return Priceutills.round(itemTotal().multiply(taxRate));
    }

    public BigDecimal total() {
        return Priceutills.round(itemTotal().add(tax()));
    }
}

