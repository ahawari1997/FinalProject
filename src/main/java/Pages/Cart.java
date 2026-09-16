package Pages;

import Exceptions.ProductNotFoundException;
import Models.Products;
import Utils.Priceutills;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class Cart extends BasePage {
    private static final By CART_LIST = By.className("cart_list");
    private static final By CART_ITEM = By.className("cart_item");
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By ITEM_PRICE = By.className("inventory_item_price");
    private static final By ITEM_QUANTITY = By.className("cart_quantity");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    WebDriver driver;
    public Cart(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By Uniquelement() {
        return CART_LIST;
    }

    public List<Products> cartProducts() {
        return driver.findElements(CART_ITEM).stream()
                .map(item -> new Products(
                        item.findElement(ITEM_NAME).getText(),
                        Priceutills.parse(item.findElement(ITEM_PRICE).getText())))
                .collect(Collectors.toList());
    }
    public List<String> productNames() {
        return driver.findElements(ITEM_NAME).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int quantityOf(String productName) {
        WebElement item = findItem(productName);
        return Integer.parseInt(item.findElement(ITEM_QUANTITY).getText());
    }



    public int rowCount() {
        return driver.findElements(CART_ITEM).size();
    }

    public Cart removeItem(String productName) {
        WebElement item = findItem(productName);
        item.findElement(By.cssSelector("button[id^='remove']")).click();
        return this;
    }
    public  Product continueShopping() {
        click(CONTINUE_SHOPPING_BUTTON);
        return new Product(driver);
    }

    public CheckOutInfo checkout() {
        click(CHECKOUT_BUTTON);
        return new CheckOutInfo(driver);
    }

    private WebElement findItem(String productName) {
        return driver.findElements(CART_ITEM).stream()
                .filter(item -> item.findElement(ITEM_NAME).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productName, productNames()));
    }
}

