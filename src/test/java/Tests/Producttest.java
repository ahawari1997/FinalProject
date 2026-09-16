package Tests;

import Core.Base;
import Exceptions.ProductNotFoundException;
import Models.Products;
import Pages.Cart;
import Pages.Product;
import Pages.ProductDetailsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@Epic("Final project Swag Labs")
@Feature("Products catalogue")
public class Producttest extends Base {
    private static final String KNOWN_PRODUCT = "Sauce Labs Backpack";
    private static final String PRODUCT_A = "Sauce Labs Backpack";
    private static final String PRODUCT_B = "Sauce Labs Bike Light";


    @Test(groups = "smoke")
    @Story("Catalogue lists all products")
    @Severity(SeverityLevel.CRITICAL)
    public void allProductsAreListed() {
        Product productsPage = signIn();
        List<Products> products = productsPage.allProducts();

        Assert.assertEquals(products.size(), 6,
                "Expected exactly 6 products in the Sauce Demo catalogue, found " + products.size());
        for (Products product : products) {
            Assert.assertTrue(product.price().compareTo(BigDecimal.ZERO) > 0,
                    "Expected " + product.name() + " to have a price greater than 0, was " + product.price());
        }
    }
    @Test(groups = "regression")
    @Story("Sorting by price")
    @Severity(SeverityLevel.NORMAL)
    public void sortingByPriceLowToHigh() {
        Product productsPage = signIn();
        productsPage.sortByPriceLowToHigh();

        List<Products> products = productsPage.allProducts();
        List<BigDecimal> prices = new ArrayList<>();
        products.forEach(p -> prices.add(p.price()));

        List<BigDecimal> sorted = new ArrayList<>(prices);
        sorted.sort(BigDecimal::compareTo);

        Assert.assertEquals(prices, sorted, "Expected prices to be ascending after sorting low to high");
    }
    @Test(groups = "regression")
    @Story("Sorting by name")
    @Severity(SeverityLevel.NORMAL)
    public void sortingByNameZtoA() {
        Product productsPage = signIn();
        productsPage.sortByNameZtoA();

        List<String> names = productsPage.productNamesInOrder();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(java.util.Comparator.reverseOrder());

        Assert.assertEquals(names, sorted, "Expected names to be reverse-alphabetical after sorting Z to A");
    }
    @Test(groups = "regression")
    @Story("Details page matches catalogue")
    @Severity(SeverityLevel.NORMAL)
    public void productDetailsMatchTheCatalogue() {
        Product productsPage = signIn();
        Products catalogueProduct = productsPage.allProducts().stream()
                .filter(p -> p.name().equals(KNOWN_PRODUCT))
                .findFirst()
                .orElseThrow();

        ProductDetailsPage detailsPage = productsPage.openProductDetails(KNOWN_PRODUCT);
        Products detailsProduct = detailsPage.productt();

        Assert.assertEquals(detailsProduct.name(), catalogueProduct.name(),
                "Product name on details page should match the catalogue");
        Assert.assertEquals(detailsProduct.price(), catalogueProduct.price(),
                "Product price on details page should match the catalogue");
    }

    @Test(groups = "regression", expectedExceptions = ProductNotFoundException.class)
    @Story("Unknown product raises a clear error")
    @Severity(SeverityLevel.MINOR)
    public void unknownProductRaisesAClearError() {
        Product productsPage = signIn();
        productsPage.addToCart("This Product Does Not Exist");
    }
    @Test(groups = "regression")
    @Story("Continue shopping keeps the cart")
    @Severity(SeverityLevel.NORMAL)
    public void continueShoppingKeepsTheCart() {
        Product productsPage = signIn();
        productsPage.addToCart(PRODUCT_A);

        Cart cartPage = productsPage.openCart();
        Product backOnProducts = cartPage.continueShopping();

        Assert.assertEquals(backOnProducts.pageTitle(), "Products", "Should be back on the products page");
        Assert.assertEquals(backOnProducts.cartBadgeCount(), 1, "Cart badge should still show 1 item");
        Assert.assertTrue(backOnProducts.isInCart(PRODUCT_A), PRODUCT_A + " should still show as added");
    }
}
