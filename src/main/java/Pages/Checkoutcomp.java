package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Checkoutcomp extends BasePage{
    private static final By COMPLETE_HEADER = By.className("complete-header");
    private static final By BACK_HOME_BUTTON = By.id("back-to-products");
    WebDriver driver;

     public Checkoutcomp(WebDriver driver){
         super(driver);

     }

    @Override
    protected By Uniquelement() {
        return COMPLETE_HEADER;
    }

    public String confirmationMessage() {
        return textof(COMPLETE_HEADER);
    }

    public Product backToProducts() {
        click(BACK_HOME_BUTTON);
        return new Product(driver);
    }
}
