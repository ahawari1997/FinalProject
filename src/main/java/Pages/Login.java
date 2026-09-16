package Pages;

import Core.Base;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Login extends BasePage {
    WebDriver driver;
    WebDriverWait wait;
    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By CREDENTIALS_PANEL = By.id("login_credentials");
    private static final By LOGO = By.className("login_logo");
    public Login(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait= new WebDriverWait(driver,Duration.ofSeconds(3));
    }


    @Override
    protected By Uniquelement() {
        return LOGIN_BUTTON;
    }
    public static Login open(WebDriver driver) {
        driver.navigate().to("https://www.saucedemo.com/");
        return new Login(driver);
    }
    /**
     * Logs in with the given credentials and returns the resulting page.
     */

    public Product logins(String username, String password) {
        type(USERNAME_INPUT, username);
        type(PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
        return new Product(driver);
    }

    public Login loginExpectingFailure(String username, String password) {
        type(USERNAME_INPUT, username);
        type(PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
        return this;
    }

    public  By getErrorMessage() {
        return ERROR_MESSAGE;
    }
    public boolean isLogoDisplayed() {
        return !driver.findElements(LOGO).isEmpty() && driver.findElement(LOGO).isDisplayed();
    }
    public boolean isUsernameFieldDisplayed() {
        return !driver.findElements(USERNAME_INPUT).isEmpty() && driver.findElement(USERNAME_INPUT).isDisplayed();
    }
    public boolean isPasswordFieldDisplayed() {
        return !driver.findElements(PASSWORD_INPUT).isEmpty() && driver.findElement(PASSWORD_INPUT).isDisplayed();
    }
    public boolean isLoginButtonDisplayed() {
        return !driver.findElements(LOGIN_BUTTON).isEmpty() && driver.findElement(LOGIN_BUTTON).isDisplayed();
    }

    private boolean isCredentialsPanelDisplayed() {
        return !driver.findElements(CREDENTIALS_PANEL).isEmpty() && driver.findElement(CREDENTIALS_PANEL).isDisplayed();
    }
}
