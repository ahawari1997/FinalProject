package Pages;

import Exceptions.ProductNotFoundException;
import Models.Products;
import Utils.Priceutills;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class Product extends BasePage {
    private static final By INVENTORY_LIST = By.className("inventory_list");
    private static final By INVENTORY_ITEM = By.className("inventory_item");
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By ITEM_PRICE = By.className("inventory_item_price");
    private static final By SORT_DROPDOWN = By.className("product_sort_container");

    public Product(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By Uniquelement() {
        return INVENTORY_LIST;
    }


    public List<Products> allProducts() {
        return driver.findElements(INVENTORY_ITEM).stream()
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    public List<String> productNamesInOrder() {
        return driver.findElements(ITEM_NAME).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private Products toProduct(WebElement item) {
        String name = item.findElement(ITEM_NAME).getText();
        String priceText = item.findElement(ITEM_PRICE).getText();
        return new Products(name, Priceutills.parse(priceText));
    }

    public void sortByPriceLowToHigh() {
        selectbyvisible(SORT_DROPDOWN, "Price (low to high)");
    }

    public void sortByPriceHighToLow() {
        selectbyvisible(SORT_DROPDOWN, "Price (high to low)");
    }

    public void sortByNameAtoZ() {
        selectbyvisible(SORT_DROPDOWN, "Name (A to Z)");
    }

    public void sortByNameZtoA() {
        selectbyvisible(SORT_DROPDOWN, "Name (Z to A)");
    }

    public Product addToCart(String productName) {
        WebElement item = findItem(productName);
        item.findElement(By.cssSelector("button[id^='add-to-cart']")).click();
        return this;
    }

    public Product removeFromCart(String productName) {
        WebElement item = findItem(productName);
        item.findElement(By.cssSelector("button[id^='remove']")).click();
        return this;
    }

    public boolean isInCart(String productName) {
        WebElement item = findItem(productName);
        return !item.findElements(By.cssSelector("button[id^='remove']")).isEmpty();
    }

    public ProductDetailsPage openProductDetails(String productName) {
        WebElement item = findItem(productName);
        item.findElement(ITEM_NAME).click();
        return new ProductDetailsPage(driver);
    }

    private WebElement findItem(String productName) {
        return driver.findElements(INVENTORY_ITEM).stream()
                .filter(item -> item.findElement(ITEM_NAME).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(productName, productNamesInOrder()));
    }



}
