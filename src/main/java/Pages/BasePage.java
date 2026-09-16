package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasePage {
protected WebDriver driver;
protected static WebDriverWait wait;
    private static final By BURGER_MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By PAGE_TITLE = By.className("title");
public BasePage(WebDriver driver){
    this.driver=driver;
    this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
}

    protected By Uniquelement() {
        return BURGER_MENU_BUTTON ;
    }

    public void waitforopen(){
wait.until(ExpectedConditions.visibilityOfElementLocated(Uniquelement()));

}
protected static WebElement waitvisible(By locator){
return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}
protected WebElement waitclick(By locator){
    return wait.until(ExpectedConditions.elementToBeClickable(locator));
}
protected void click(By locator){
    waitclick(locator).click();
}
    protected void type(By locator, String text) {
        WebElement element = waitvisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    protected static String textof(By locator){
    return waitvisible(locator).getText();
    }
    protected void selectbyvisible(By loctor , String visibltext){
        Select select=new Select(waitvisible(loctor));
        select.selectByVisibleText(visibltext);
    }
    protected void scrollIntoView(By locator) {
        WebElement element = waitvisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }
    public static String pageTitle() {
        return textof(PAGE_TITLE);
    }
    public int cartBadgeCount() {
        if (driver.findElements(CART_BADGE).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(textof(CART_BADGE));
    }

    public Cart openCart() {
        click(CART_LINK);
        return new Cart(driver);
    }

    public Login logout() {
        click(BURGER_MENU_BUTTON);
        click(LOGOUT_LINK);
        return new Login(driver) ;
    }




    public File takeScreenshot(String testName) {
        try {
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS"));
            File destination = dir.resolve(testName + "_" + timestamp + ".png").toFile();

            org.openqa.selenium.TakesScreenshot camera = (org.openqa.selenium.TakesScreenshot) driver;
            File source = camera.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            Files.copy(source.toPath(), destination.toPath());
           return destination;
       } catch (Exception e) {
            throw new Exceptions.FramworkException(
                    "Failed to capture screenshot for test '" + testName + "'", e);
        }
    }
}
