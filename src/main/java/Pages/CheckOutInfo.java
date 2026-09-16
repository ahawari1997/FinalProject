package Pages;

import Models.Customer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutInfo extends BasePage{
    private static final By FIRST_NAME_INPUT = By.id("first-name");
    private static final By LAST_NAME_INPUT = By.id("last-name");
    private static final By POSTAL_CODE_INPUT = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public CheckOutInfo(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By Uniquelement() {
        return CONTINUE_BUTTON;
    }

    public CheckoutOver fillAndContinue(Customer customer) {
        fillForm(customer);
        click(CONTINUE_BUTTON);
        return new CheckoutOver(driver);
    }

    public CheckOutInfo submitExpectingFailure(Customer customer) {
        fillForm(customer);
        click(CONTINUE_BUTTON);
        return this;
    }

    private void fillForm(Customer customer) {
        if (customer.firstName() != null && !customer.firstName().isBlank()) {
            type(FIRST_NAME_INPUT, customer.firstName());
        }
        if (customer.lastName() != null && !customer.lastName().isBlank()) {
            type(LAST_NAME_INPUT, customer.lastName());
        }
        if (customer.postalCode() != null && !customer.postalCode().isBlank()) {
            type(POSTAL_CODE_INPUT, customer.postalCode());
        }
    }

    public String errorMessage() {
        return textof(ERROR_MESSAGE);
    }

    public Product cancel() {
        click(CANCEL_BUTTON);
        return new Product(driver);
    }
}
