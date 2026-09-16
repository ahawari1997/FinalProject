package Core;

import Exceptions.FramworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
          }


    public WebDriver Start(){
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    return driver;
}
    public static void create() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        DRIVER.set(driver);
    }
    public static WebDriver get() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new FramworkException(
                    "No WebDriver has been created for thread '" + Thread.currentThread().getName()
                            + "'. Did you forget to call DriverFactory.create() in a @Before method?");
        }
        return driver;
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}