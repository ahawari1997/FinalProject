package Pages;

import Core.Base;
import Models.Products;
import Utils.Priceutills;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailsPage extends BasePage {
private WebDriver driver;
private WebDriverWait wait;

    private static final By DETAILS_CONTAINER = By.className("inventory_details_name");
    private static final By NAME = By.className("inventory_details_name");
    private static final By PRICE = By.className("inventory_details_price");
    private static final By DESCRIPTION = By.className("inventory_details_desc");
    private static final By ADD_TO_CART_BUTTON = By.cssSelector("button[id^='add-to-cart']");
    private static final By BACK_BUTTON = By.id("back-to-products");

public ProductDetailsPage(WebDriver driver){
    super(driver);
    this.driver=driver;
    this.wait=new WebDriverWait(driver, Duration.ofSeconds(5));
}

    @Override
    protected By Uniquelement() {
        return DETAILS_CONTAINER;
    }

    protected By uniqueElement() {
        return DETAILS_CONTAINER;
    }

    public Products productt() {
        String name = textof(NAME);
        String priceText = textof(PRICE);
        String description = textof(DESCRIPTION);
        return new Products(name, Priceutills.parse(priceText), description);
    }


    public ProductDetailsPage addToCart() {
        click(ADD_TO_CART_BUTTON);
        return this;
    }

    public Product backToProducts() {
        click(BACK_BUTTON);
        return new Product(driver);
    }

}
