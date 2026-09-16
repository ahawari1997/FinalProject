package Tests;

import Core.Base;
import Pages.Login;
import Pages.Product;
import Swaglabs.dataproviders.DataProviders;
import io.qameta.allure.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.remote.http.AddSeleniumUserAgent;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Epic("Swaglab")
@Feature("Login")
public class Logintest extends Base {
    @Test(groups = "smoke")
    @Story("Successful Login")
    @Severity(SeverityLevel.BLOCKER)
    public void standardUserCanLogIn(){
        Product productpage=signIn();
        Assert.assertTrue(driver().getCurrentUrl().endsWith("/inventory.html"),
                "Expected to land on /inventory.html after a valid login, but was on: " + driver().getCurrentUrl());
        Assert.assertEquals(Product.pageTitle(), "Products",
                "Expected the page heading to read 'Products' after logging in");
    }

    @Test(groups = "regression", dataProvider = "rejectedLogins",
            dataProviderClass = DataProviders.class)
    @Story("Rejected login shows the right error")
    @Severity(SeverityLevel.CRITICAL)
public void Rejectloginshowtherighterror(String username, String Password,String expectedError){
        Login login= openLoginPage().loginExpectingFailure(username,Password);
        Assert.assertTrue(login.getErrorMessage().equals(expectedError),
                "Expected error message to contain '" + expectedError
                        + "' but was '" + login.getErrorMessage() + "'");

    }
    @Test(groups = "regression")
    @Story("Login page renders correctly")
    @Severity(SeverityLevel.NORMAL)
    public void loginPageRendersCorrectly() {
        Login loginPage = openLoginPage();
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(loginPage.isLogoDisplayed(), "Sauce Labs logo should be visible");
        softAssert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be visible");
        softAssert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be visible");
        softAssert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible");
        softAssert.assertAll();
    }

    @Test(groups = "regression", dependsOnMethods = "standardUserCanLogIn")
    @Story("Logout returns to the login page")
    @Severity(SeverityLevel.NORMAL)
    public void userCanLogOut() {
        Product productsPage = signIn();
        Login loginPage = productsPage.logout();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Expected to be back on the login page after logging out");
        Assert.assertTrue(driver().getCurrentUrl().equals("https://www.saucedemo.com")
                        || driver().getCurrentUrl().equals("https://www.saucedemo.com" + "/"),
                "Expected to be back at the base URL after logout, but was on: " + driver().getCurrentUrl());
    }


}
