package Tests;

import Core.Base;
import Models.Products;
import Pages.Cart;
import Pages.Product;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class Carttest extends Base {
    private static final String PRODUCT_A = "Sauce Labs Backpack";
    private static final String PRODUCT_B = "Sauce Labs Bike Light";

    @Test(groups = "smoke")
    @Story("Cart shows the items that were added")
    @Severity(SeverityLevel.CRITICAL)
    public void cartShowsTheItemsThatWereAdded() {
        Product productsPage = signIn();
        productsPage.addToCart(PRODUCT_A);
        productsPage.addToCart(PRODUCT_B);

        Cart cartPage = productsPage.openCart();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(cartPage.rowCount(), 2, "Cart should have exactly 2 rows");
        softAssert.assertTrue(cartPage.productNames().contains(PRODUCT_A), PRODUCT_A + " should be listed in the cart");
        softAssert.assertTrue(cartPage.productNames().contains(PRODUCT_B), PRODUCT_B + " should be listed in the cart");
        softAssert.assertEquals(cartPage.quantityOf(PRODUCT_A), 1, "Quantity of " + PRODUCT_A + " should be 1");

        softAssert.assertAll();
    }
    @Test(groups = "regression")
    @Story("Cart prices match the catalogue")
    @Severity(SeverityLevel.CRITICAL)
    public void cartPricesMatchTheCatalogue() {
        Product productsPage = signIn();
        List<Products> catalogue = productsPage.allProducts();
        productsPage.addToCart(PRODUCT_A);
        productsPage.addToCart(PRODUCT_B);

        Cart cartPage = productsPage.openCart();
        List<Products> cartProducts = cartPage.cartProducts();

        for (Products cartProduct : cartProducts) {
            Products catalogueMatch = catalogue.stream()
                    .filter(p -> p.name().equals(cartProduct.name()))
                    .findFirst()
                    .orElseThrow();

            Assert.assertEquals(cartProduct.price(), catalogueMatch.price(),
                    "Price of " + cartProduct.name() + " in cart should match the catalogue");
        }
    }

    @Test(groups = "regression")
    @Story("Removing an item updates the cart and the badge")
    @Severity(SeverityLevel.NORMAL)
    public void removingAnItemUpdatesTheCartAndTheBadge() {
        Product productsPage = signIn();
        productsPage.addToCart(PRODUCT_A);
        productsPage.addToCart(PRODUCT_B);

        Cart cartPage = productsPage.openCart();
        cartPage.removeItem(PRODUCT_A);

        Assert.assertEquals(cartPage.rowCount(), 1, "Cart should have 1 row left after removing an item");
        Assert.assertFalse(cartPage.productNames().contains(PRODUCT_A), PRODUCT_A + " should no longer be in the cart");
        Assert.assertEquals(cartPage.cartBadgeCount(), 1, "Badge should drop to 1 after removing an item from the cart");
    }


}
