package Pages;

import Models.Products;
import Utils.Priceutills;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutOver extends BasePage {
    private static final By CART_ITEM = By.className("cart_item");
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By ITEM_PRICE = By.className("inventory_item_price");
    private static final By SUBTOTAL_LABEL = By.className("summary_subtotal_label");
    private static final By TAX_LABEL = By.className("summary_tax_label");
    private static final By TOTAL_LABEL = By.className("summary_total_label");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By CANCEL_BUTTON = By.id("cancel");
public CheckoutOver (WebDriver driver){
    super(driver);
}

    @Override
    protected By Uniquelement() {
        return FINISH_BUTTON;
    }
    public List<Products> lineItems() {
        return driver.findElements(CART_ITEM).stream()
                .map(item -> new Products(item.findElement(ITEM_NAME).getText(),
                        Priceutills.parse(item.findElement(ITEM_PRICE).getText()))).collect(Collectors.toList());
    }

    //Reads "Item total: $29.99" -> 29.99, as displayed on the page. */
    public BigDecimal displayedItemTotal() {
        return Priceutills.parse(textof(SUBTOTAL_LABEL).replace("Item total:", ""));
    }

    public BigDecimal displayedTax() {
        return Priceutills.parse(textof(TAX_LABEL).replace("Tax:", ""));
    }

    public BigDecimal displayedTotal() {
        return Priceutills.parse(textof(TOTAL_LABEL).replace("Total:", ""));
    }

    public Checkoutcomp finish() {
        click(FINISH_BUTTON);
        return new Checkoutcomp(driver);
    }

    public Product cancel() {
        click(CANCEL_BUTTON);
        return new Product(driver);
    }


}
