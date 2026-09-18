package Tests;

import Core.Base;
import Models.Customer;
import Pages.Cart;
import Pages.CheckOutInfo;
import Pages.Product;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Checkouttest extends Base {
    private static final String PRODUCT_A = "Sauce Labs Backpack";
    private static final String PRODUCT_B = "Sauce Labs Bike Light";

    @Test(groups = "regression", dataProvider = "missingCheckoutFields",
            dataProviderClass = Swaglabs.dataproviders.DataProviders.class)
    @Story("Missing fields are rejected")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test the missing fields")
    public void missingFieldsAreRejected(String firstName, String lastName, String postalCode, String expectedErrorFragment) {
        Product productsPage = signIn();
        productsPage.addToCart(PRODUCT_A);
        Cart cartPage = productsPage.openCart();
        CheckOutInfo checkoutInfo = cartPage.checkout();

        Customer customer = new Customer(firstName, lastName, postalCode, PRODUCT_A);
        checkoutInfo = checkoutInfo.submitExpectingFailure(customer);

        Assert.assertTrue(checkoutInfo.errorMessage().contains(expectedErrorFragment),
                "Expected error to contain '" + expectedErrorFragment + "' but was '" + checkoutInfo.errorMessage() + "'");
    }
}
