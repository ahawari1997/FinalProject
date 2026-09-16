package Core;

import Listener.TestListener;
import Pages.Login;
import Pages.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.*;

import javax.swing.*;



@Listeners (TestListener.class)
public abstract class Base {
    protected static final Logger LOGGER = LogManager.getLogger(Base.class);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        LOGGER.info("=== Suite '{}' starting ===");
    }
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        LOGGER.info("=== Class {} starting ===", getClass().getSimpleName());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        DriverFactory.create();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        DriverFactory.quit();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        LOGGER.info("=== Class {} finished ===", getClass().getSimpleName());
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        LOGGER.info("=== Suite finished ===");
    }

    protected WebDriver driver() {
        return DriverFactory.get();
    }
    protected Login openLoginPage() {
        return Login.open(driver());
    }

    protected Product signIn() {
//
        return openLoginPage().logins("standard_user","secret_sauce");
    }

    protected Product signIn(String username, String password) {
        return openLoginPage().logins(username, password);
    }
}
