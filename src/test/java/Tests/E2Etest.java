package Tests;

import Core.Base;
import Models.CartSummery;
import Models.Customer;
import Models.Products;
import Pages.*;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

public class E2Etest extends Base {
    @Test(groups = "e2e", dataProvider = "customers",
            dataProviderClass = Swaglabs.dataproviders.DataProviders.class)
    @Story("Customer can complete a purchase")
    @Severity(SeverityLevel.BLOCKER)
    public void customerCanCompleteAPurchase(Customer customer) {
        Product productsPage = signIn();
        productsPage.addToCart(customer.productToBuy());

        Cart cartPage = productsPage.openCart();
        Assert.assertTrue(cartPage.productNames().contains(customer.productToBuy()),
                "Cart should contain " + customer.productToBuy() + " before checkout");

        CheckOutInfo checkoutInfo = cartPage.checkout();
        CheckoutOver overview = checkoutInfo.fillAndContinue(customer);

        List<Products> lineItems = overview.lineItems();
        CartSummery expected = new CartSummery(lineItems, BigDecimal.valueOf(0.08));
        Assert.assertEquals(overview.displayedTotal(), expected.total(),
                "Grand total for " + customer + " should match the Java-calculated total");

        Checkoutcomp completePage = overview.finish();
        Assert.assertEquals(completePage.confirmationMessage(), "Thank you for your order!",
                "Order for " + customer + " should be confirmed");
        Assert.assertEquals(completePage.cartBadgeCount(), 0, "Cart badge should be empty once the order is placed");
    }
    @Test(groups = "e2e")
    @Story("Cancelling halfway keeps the basket")
    @Severity(SeverityLevel.CRITICAL)
    public void cancellingHalfwayKeepsTheBasket() {
        String productA = "Sauce Labs Backpack";
        String productB = "Sauce Labs Bike Light";

        Product productsPage = signIn();
        productsPage.addToCart(productA);
        productsPage.addToCart(productB);

        Cart cartPage = productsPage.openCart();
        CheckOutInfo checkoutInfo = cartPage.checkout();
        Customer customer = new Customer("John", "Doe", "10001", productA);
        CheckoutOver overview = checkoutInfo.fillAndContinue(customer);

        Product backOnProducts = overview.cancel();

        Assert.assertEquals(backOnProducts.cartBadgeCount(), 2,
                "Basket should still hold both items after cancelling halfway through checkout");
        Assert.assertTrue(backOnProducts.isInCart(productA), productA + " should remain in the basket");
        Assert.assertTrue(backOnProducts.isInCart(productB), productB + " should remain in the basket");
    }

}
